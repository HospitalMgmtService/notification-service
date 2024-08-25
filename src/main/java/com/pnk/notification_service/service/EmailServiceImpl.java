package com.pnk.notification_service.service;

import com.pnk.notification_service.dto.request.EmailRequest;
import com.pnk.notification_service.dto.request.SendEmailRequest;
import com.pnk.notification_service.dto.request.Sender;
import com.pnk.notification_service.dto.response.EmailResponse;
import com.pnk.notification_service.entity.EmailRecord;
import com.pnk.notification_service.exception.AppException;
import com.pnk.notification_service.exception.ErrorCode;
import com.pnk.notification_service.repository.EmailRecordRepository;
import com.pnk.notification_service.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor // injected by Constructor, no longer need of @Autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailServiceImpl implements EmailService {

    EmailClient emailClient;

    EmailRecordRepository emailRecordRepository;

    @NonFinal
    @Value("${email.api-key}")
    protected String apiKey;

    @Override
    public EmailResponse deliverEmail(SendEmailRequest sendEmailRequest) {
        log.info(">> deliverEmail::sendEmailRequest: {}", sendEmailRequest);

        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("PNK Solutions")
                        .email("vhphong@gmail.com")
                        .build())
                .to(List.of(sendEmailRequest.getTo()))
                .subject(sendEmailRequest.getSubject())
                .htmlContent(sendEmailRequest.getHtmlContent())
                .build();

        log.info(">> deliverEmail::emailRequest: {}", emailRequest);

        EmailResponse emailResponse;
        try {
            emailResponse = emailClient.sendEmail(apiKey, emailRequest);
            recordEmailInDatabase(emailRequest, emailResponse);
        } catch (FeignException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }

        return emailResponse;
    }


    /*
     * create a record of delivered email in database for following up
     * */
    @Override
    public void recordEmailInDatabase(EmailRequest emailRequest, EmailResponse emailResponse) {
        EmailRecord emailRecord = new EmailRecord();
        emailRecord.setEmailRequest(emailRequest);
        emailRecord.setEmailResponse(emailResponse);
        emailRecord.setSentDateTime(LocalDateTime.now());

        emailRecordRepository.save(emailRecord);
    }

}
