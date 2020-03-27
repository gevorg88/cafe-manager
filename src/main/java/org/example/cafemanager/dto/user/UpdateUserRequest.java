package org.example.cafemanager.dto.user;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

public class UpdateUserRequest {
    @NotBlank(message = "First Name is required")
    @Length(max = 32, min = 6, message = "First name must contain at from 6 to 32 symbols")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Length(max = 32, min = 6, message = "Last name must contain at from 6 to 32 symbols")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
