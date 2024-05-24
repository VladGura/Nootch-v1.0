package com.nootch.services;

import org.springframework.web.multipart.MultipartFile;

public interface EmailService {
    void sendMail(/* MultipartFile[] file, */ String to, String subject, String body);
}
