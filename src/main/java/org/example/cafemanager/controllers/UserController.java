package org.example.cafemanager.controllers;

import org.example.cafemanager.dto.user.CreateAjaxResponse;
import org.example.cafemanager.dto.user.UserCreate;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.user.UserCreateRequestBody;
import org.example.cafemanager.dto.user.UserPublicProps;
import org.example.cafemanager.services.user.Exceptions.MustBeUniqueException;
import org.example.cafemanager.services.user.contracts.UserService;
import org.example.cafemanager.utilities.MailConstructor;
import org.example.cafemanager.utilities.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final MailConstructor mailConstructor;

    @Autowired
    public UserController(final UserService userService, final MailConstructor mailConstructor) {
        this.userService = userService;
        this.mailConstructor = mailConstructor;
    }

    @PostMapping
    public ResponseEntity<?> store(
            @Valid @RequestBody UserCreateRequestBody requestBody,
            Errors errors
    ) {
        String password = SecurityUtility.randomPassword();
        CreateAjaxResponse result = new CreateAjaxResponse();
        if (errors.hasErrors()) {
            result.setMessage(errors.getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n")));
            return ResponseEntity.unprocessableEntity().body(result);
        }

        UserCreate createDto = new UserCreate(requestBody.getUsername(),
                password,
                requestBody.getEmail());
        try {
            createDto.setFirstName(requestBody.getFirstName());
            createDto.setLastName(requestBody.getLastName());
            userService.createUser(createDto, Role.WAITER);
            result.setMessage("User has been successfully created");
        } catch (MustBeUniqueException e) {
            result.setMessage(e.getMessage());
            ResponseEntity.unprocessableEntity().body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }

        return ResponseEntity.ok(result);
//        this.mailConstructor.userInviteEmail(user, password);
    }

    @GetMapping
    public String index(Model model) {
        Collection<UserPublicProps> users = userService.getAllWaiters();
        model.addAttribute("users", users);
//        model.addAttribute("users", userService.findAll());
        return "user/list";
    }
}
