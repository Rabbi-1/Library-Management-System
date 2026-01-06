package com.rabbi.services;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
