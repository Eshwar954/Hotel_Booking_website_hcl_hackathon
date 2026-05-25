package com.example.backend.service;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendBookingConfirmation(String to, String content) {
        // TODO: integrate with email provider
    }
}
