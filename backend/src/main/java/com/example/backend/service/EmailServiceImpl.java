package com.example.backend.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl
        implements EmailService {

    private final JavaMailSender
            mailSender;

    public EmailServiceImpl(
            ObjectProvider<JavaMailSender> mailSenderProvider
    ) {
        this.mailSender =
                mailSenderProvider.getIfAvailable();
    }

    @Override
    public void sendBookingConfirmation(
            String to,
            String subject,
            String content
    ) {

        if (mailSender == null) {
            return;
        }

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);

        message.setSubject(subject);

        message.setText(content);

        mailSender.send(message);
    }
}
