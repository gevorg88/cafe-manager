package org.example.cafemanager.dto.user;

import java.io.Serializable;

public class CreateUserRequest implements Serializable {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

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
