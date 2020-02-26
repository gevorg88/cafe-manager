package org.example.cafemanager.controllers;

import org.example.cafemanager.dto.user.UserCreate;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.services.user.contracts.UserService;
import org.example.cafemanager.utilities.MailConstructor;
import org.example.cafemanager.utilities.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "users")
public class UserController {
    private final UserService userService;

    private final MailConstructor mailConstructor;

    @Autowired
    public UserController(final UserService userService, final MailConstructor mailConstructor) {
        this.userService = userService;
        this.mailConstructor = mailConstructor;
    }

    @PostMapping(value = "/create")
    public String store(
            @RequestParam("email") String userEmail,
            @RequestParam("username") String username,
            Model model
    ) throws Exception {
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);

        if (userService.findByUsername(username) != null) {
            model.addAttribute("usernameExists", true);
            return "myAccount";
        }

        if (userService.findByEmail(userEmail) != null) {
            model.addAttribute("email", true);
            return "myAccount";
        }

        String password = SecurityUtility.randomPassword();

        User user = userService.createUser(new UserCreate(
                username,
                password,
                userEmail
        ), Role.WAITER);

//        this.mailConstructor.userInviteEmail(user, password);
        model.addAttribute("mailIsSent", "true");
        return "myAccount";
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("user", userService.findAll());
        return "users.list";
    }
}
