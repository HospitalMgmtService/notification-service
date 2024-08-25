package com.pnk.notification_service.repository;

import com.pnk.notification_service.entity.EmailRecord;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EmailRecordRepository extends MongoRepository<EmailRecord, String> {
}
