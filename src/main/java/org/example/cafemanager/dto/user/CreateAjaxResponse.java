package org.example.cafemanager.dto.user;

import org.springframework.stereotype.Component;

@Component
public class CreateAjaxResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
