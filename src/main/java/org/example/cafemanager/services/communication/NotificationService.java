package org.example.cafemanager.services.communication;

import org.example.cafemanager.dto.user.CreateUserRequest;

public interface NotificationService {

    void notify(CreateUserRequest user);
}
