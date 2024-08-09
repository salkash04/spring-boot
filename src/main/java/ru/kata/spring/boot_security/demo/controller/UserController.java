package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
public class UserController {

    private final UserServiceImp userServiceImp;

    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/")
    public String sayHome() {
        return "index";
    }

    @GetMapping("/user")
    public String sayUsers(Principal principal, Model model) {
        model.addAttribute("user", userServiceImp.findByUsername(principal.getName()));
        return "user";
    }
}