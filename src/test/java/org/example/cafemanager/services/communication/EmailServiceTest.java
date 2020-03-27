package org.example.cafemanager.services.communication;

import org.example.cafemanager.domain.User;
import org.example.cafemanager.dto.user.CreateUserRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.MailSender;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private MailSender mailSender;

    @Test(expected = IllegalArgumentException.class)
    public void testNotifyWithInvalidArguments() {

        emailService.notify(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotifyWithInvalidEmail () {

        CreateUserRequest u = new CreateUserRequest();

        emailService.notify(u);

    }

    @Test
    public void testNotify() {
        CreateUserRequest u = new CreateUserRequest("asd", "asd", "asd");
        emailService.notify(u);
        Mockito.verify(jmsTemplate, Mockito.only()).convertAndSend("mailbox", u);
    }
}
