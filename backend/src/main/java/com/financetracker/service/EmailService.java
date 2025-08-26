package com.financetracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// Email service for OTP emails, useing gmail SMTP
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendOtpEmail(String toEmail, String otp, String purpose) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setFrom("financetracker666@gmail.com");
            
            if ("registration".equals(purpose)) {
                message.setSubject("Expenso - Email Verification");
                message.setText("Welcome to Expenso!\nYour email verification OTP is: " + otp + 
                              "\nThis OTP will expire in 5 minutes.\nThank you!");
            } else if ("login".equals(purpose)) {
                message.setSubject("Expenso - Login Verification");
                message.setText("Login OTP for Expenso:\nYour OTP is: " + otp + 
                              "\n\nThis OTP will expire in 5 minutes.");
            } else if ("forgot-password".equals(purpose)) {
                message.setSubject("Expenso - Password Reset");
                message.setText("Password Reset OTP for Expenso:\n\nYour OTP is: " + otp + 
                              "\n\nThis OTP will expire in 5 minutes.");
            }

            mailSender.send(message);
            System.out.println("OTP email sent successfully to: " + toEmail);
            
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            throw new RuntimeException("Failed to send email");
        }
    }
}