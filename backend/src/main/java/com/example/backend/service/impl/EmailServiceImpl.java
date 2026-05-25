package com.hotelbooking.service.impl;

import org.springframework.stereotype.Service;

import com.hotelbooking.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendBookingConfirmation(String to, String content) {
        // TODO: integrate with email provider
    }
}
