package com.rabbi.services.impl;

import com.rabbi.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {


        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        } catch (MailException e) {
            throw new MailSendException("Failed to send email");
        }


    }



}
