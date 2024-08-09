package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;

    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/roles")
    public List<String> getAllRoles() {
        return List.of("ROLE_ADMIN", "ROLE_USER");
    }

    @GetMapping("/users")
    public List<User> sayUsers() {
        return userServiceImp.findAll();

    }

    @PostMapping("/new")
    public void create(@RequestBody User user) {
        userServiceImp.saveUser(user);
    }


    @PatchMapping("/update")
    public void updateUsers(@RequestBody User user) {
        userServiceImp.saveUser(user);
    }


    @GetMapping("/user")
    public User getCurrentUser() {
        return userServiceImp.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userServiceImp.deleteById(id);
    }
}
