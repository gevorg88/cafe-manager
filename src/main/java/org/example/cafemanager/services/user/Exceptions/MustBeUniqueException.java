package org.example.cafemanager.services.user.Exceptions;

import javax.validation.constraints.NotNull;

public class MustBeUniqueException extends RuntimeException {
    public MustBeUniqueException() {
        this("property");
    }

    public MustBeUniqueException(@NotNull String propName) {
        super(String.format("User with this %s is already exist", propName));
    }
}
