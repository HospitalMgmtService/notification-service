package com.pnk.notification_service.service;


import com.pnk.notification_service.dto.request.SendEmailRequest;
import com.pnk.notification_service.dto.response.EmailResponse;

public interface EmailService {

    EmailResponse deliverEmail(SendEmailRequest sendEmailRequest);

}
