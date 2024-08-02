package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.HashSet;
import java.util.Set;

@Repository
public class UserSetRole {
    public Set<Role> setRolesForUser(String roleAdmin, String roleUser) {
        Set<Role> setRole = new HashSet<>();
        Role userRole = new Role();
        Role userAdmin = new Role();
        if (roleAdmin != null) {
            userRole.setId(2L);
            userRole.setName("ROLE_ADMIN");
            setRole.add(userRole);
        }
        if (roleUser != null) {
            userAdmin.setId(1L);
            userAdmin.setName("ROLE_USER");
            setRole.add(userAdmin);
        }
        return setRole;
    }

    public boolean checkNullEditUser(String id, String username, String password, String email) {
        if (username == "" || password == "" || email == "") {
            return false;
        }
        return true;
    }
}
