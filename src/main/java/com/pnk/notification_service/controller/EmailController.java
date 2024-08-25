package com.pnk.notification_service.controller;

import com.pnk.notification_service.dto.request.ApiResponse;
import com.pnk.notification_service.dto.request.SendEmailRequest;
import com.pnk.notification_service.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor // injected by Constructor, no longer need of @Autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailController {

    EmailService emailService;


    @PostMapping("/send")
    public ApiResponse dispatchEmail(@RequestBody SendEmailRequest sendEmailRequest) {
        log.info(">> dispatchEmail::sendEmailRequest: {}", sendEmailRequest);

        return ApiResponse.builder()
                .result(emailService.deliverEmail(sendEmailRequest))
                .build();
    }


    @KafkaListener(topics = "onboard-successful")
    public void listen(String message) {
        log.info(">> listen::message: {}", message);
    }

}
