package com.pnk.notification_service.service;

import com.pnk.notification_service.dto.request.EmailRequest;
import com.pnk.notification_service.dto.request.SendEmailRequest;
import com.pnk.notification_service.dto.request.Sender;
import com.pnk.notification_service.dto.response.EmailResponse;
import com.pnk.notification_service.exception.AppException;
import com.pnk.notification_service.exception.ErrorCode;
import com.pnk.notification_service.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor // injected by Constructor, no longer need of @Autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailServiceImpl implements EmailService {

    EmailClient emailClient;

    String apiKey = "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";


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

        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }

    }
}
