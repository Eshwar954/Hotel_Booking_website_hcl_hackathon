package com.example.backend.service;

public interface EmailService {

    void sendBookingConfirmation(
            String to,
            String subject,
            String content
    );
}