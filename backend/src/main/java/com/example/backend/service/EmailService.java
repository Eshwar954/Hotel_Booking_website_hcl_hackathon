package com.hotelbooking.service;

public interface EmailService {
    void sendBookingConfirmation(String to, String content);
}
