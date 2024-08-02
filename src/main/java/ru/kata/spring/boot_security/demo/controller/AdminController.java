package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;

    @Autowired
    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }



    @GetMapping("/users")
    public String sayUsers(Model model, Principal principal) {
        User user = new User();
        model.addAttribute("users", userServiceImp.findAll());
        model.addAttribute("userNav", userServiceImp.findByUsername(principal.getName()));
        model.addAttribute("roleSet", userServiceImp.getAllRoles());
        model.addAttribute("user", user);
        System.out.println(userServiceImp.findByUsername(principal.getName()));
        return "users";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("new_user") User user , @RequestParam(value = "role") String role) {
        user.setRoles(userServiceImp.findByRoleName(role));
        userServiceImp.saveUser(user);
        return "redirect:/admin/users";
    }


    @PatchMapping("/update")
    public String updateUsers(@ModelAttribute("user") User user, @RequestParam(value = "role") String role) {
        user.setRoles(userServiceImp.findByRoleName(role));
        userServiceImp.saveUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServiceImp.deleteById(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/admin_all_user")
    public String sayUsers(Principal principal,Model model) {
        model.addAttribute("user",userServiceImp.findByUsername(principal.getName()));
        return "/admin_all_user";
    }
}
