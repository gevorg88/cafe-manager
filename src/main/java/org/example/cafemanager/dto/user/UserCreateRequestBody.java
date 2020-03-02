package org.example.cafemanager.dto.user;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Component
public class UserCreateRequestBody {
    @NotBlank(message = "First Name is required")
    @Length(max = 32, message = "Your first_name is very long")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Length(max = 32, message = "Your first_name is very long")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Length(max = 32, message = "Username is very long")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
