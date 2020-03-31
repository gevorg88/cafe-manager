package org.example.cafemanager.controllers;

import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.ResponseModel;
import org.example.cafemanager.dto.user.CreateUserRequest;
import org.example.cafemanager.dto.user.UserCreateRequestBody;
import org.example.cafemanager.dto.user.UserPublicProfile;
import org.example.cafemanager.dto.user.UpdateUserRequestBody;
import org.example.cafemanager.services.exceptions.InstanceNotFoundException;
import org.example.cafemanager.services.exceptions.MustBeUniqueException;
import org.example.cafemanager.services.user.UserService;
import org.example.cafemanager.utilities.SecurityUtility;
import org.example.cafemanager.utilities.ValidationMessagesCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        Collection<UserPublicProfile> users = userService.getAllWaiters();
        model.addAttribute("users", users);
        return "user/list";
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody UserCreateRequestBody requestBody, Errors errors)
            throws Exception {
        ResponseModel result = new ResponseModel();
        if (errors.hasErrors()) {
            result.setMessage(ValidationMessagesCollector.collectErrorMessages(errors));
            return ResponseEntity.unprocessableEntity().body(result);
        }

        CreateUserRequest createDto = new CreateUserRequest(requestBody.getUsername(), SecurityUtility.randomPassword(),
                requestBody.getEmail());
        try {
            createDto.setFirstName(requestBody.getFirstName());
            createDto.setLastName(requestBody.getLastName());
            userService.create(createDto, Role.WAITER);
            result.setMessage("User has been successfully created");
        } catch (MustBeUniqueException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestBody requestBody,
            Errors errors) {
        ResponseModel result = new ResponseModel();
        if (errors.hasErrors()) {
            result.setMessage(ValidationMessagesCollector.collectErrorMessages(errors));
            return ResponseEntity.unprocessableEntity().body(result);
        }
        try {
            userService.update(id, requestBody);
            result.setMessage("User has been successfully updated");
            return ResponseEntity.ok(result);
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> destroy(@PathVariable("userId") Long userId) {
        ResponseModel result = new ResponseModel();
        try {
            userService.delete(userId);
            result.setMessage("User has been successfully deleted");
            return ResponseEntity.ok(result);
        } catch (InstanceNotFoundException e) {
            result.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            e.getMessage();
            result.setMessage("Something goes wrong! Try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
