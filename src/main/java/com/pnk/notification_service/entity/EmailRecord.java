package com.pnk.notification_service.entity;

import com.pnk.notification_service.dto.request.EmailRequest;
import com.pnk.notification_service.dto.response.EmailResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;


@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("email-record")
public class EmailRecord {

    @Id
    String id = UUID.randomUUID().toString();

    EmailRequest emailRequest;

    EmailResponse emailResponse;

    LocalDateTime sentDateTime;

}
