package org.example.cafemanager.utilities;

import java.util.Objects;

import org.example.cafemanager.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailConstructor {

    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    public SimpleMailMessage userInviteEmail(User user, String password) {
        String message = "\nPlease use on this password to access to your account. Your password is: \n" + password;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Cafe Manager App - New User");
        email.setText(message);
        email.setFrom(Objects.requireNonNull(env.getProperty("support.email")));
        return email;
    }
}
