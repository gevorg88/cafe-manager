package org.example.cafemanager.validators;

import org.example.cafemanager.domain.User;
import org.example.cafemanager.services.user.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (user.getEmail().length() < 6 || user.getEmail().length() > 32) {
            errors.rejectValue("email", "Size.userForm.email");
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "first_name", "NotEmpty");
        if (user.getFirstName().length() < 8 || user.getFirstName().length() > 32) {
            errors.rejectValue("first_name", "Size.userForm.first_name");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "last_name", "NotEmpty");
        if (user.getLastName().length() < 8 || user.getLastName().length() > 32) {
            errors.rejectValue("last_name", "Size.userForm.last_name");
        }
    }
}
