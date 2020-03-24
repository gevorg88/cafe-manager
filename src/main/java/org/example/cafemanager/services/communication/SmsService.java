package org.example.cafemanager.services.communication;

import org.example.cafemanager.dto.user.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
public class SmsService implements NotificationService {

    @Override
    public void notify(final CreateUserRequest user) {
        System.out.println("Received <" + user + ">");

    }
}
