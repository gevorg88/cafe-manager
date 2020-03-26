package org.example.cafemanager.services.communication;

import org.example.cafemanager.dto.user.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Primary
public class EmailService implements NotificationService {

    private final JmsTemplate jmsTemplate;

    private final JavaMailSender emailSender;


    @Autowired
    public EmailService(final JmsTemplate jmsTemplate, final JavaMailSender emailSender) {
        this.jmsTemplate = jmsTemplate;
        this.emailSender = emailSender;
    }

    @Override
    public void notify(final CreateUserRequest user) {
        jmsTemplate.convertAndSend("mailbox", user);
    }

    @JmsListener(destination = "mailbox", containerFactory = "jmsFactory")
    public void sendEmail(CreateUserRequest user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Invitation");
        message.setText("Hi! Please use on this password to access to your account. Your password is: " + user.getPassword());
        emailSender.send(message);
    }
}
