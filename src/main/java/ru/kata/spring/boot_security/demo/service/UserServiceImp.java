package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.repository.UserSetRole;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    UserRepository UserRepository;

    UserSetRole UserSetRole;

    @Autowired
    public void setUserDao(UserRepository userDao, UserSetRole userDaoImp) {
        this.UserRepository = userDao;
        this.UserSetRole = userDaoImp;
    }

    @Override
    public List<User> getListUsers() {
        return UserRepository.findAll();
    }
    
    public void addUser(User user) {
        UserRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = UserRepository.getById(id);
        UserRepository.delete(user);
    }

    public Set<Role> setRolesForUser(String roleAdmin, String roleUser) {
        return UserSetRole.setRolesForUser(roleAdmin, roleUser);
    }

    public User findUserById(Long id) {
        return UserRepository.getById(id);
    }

    public boolean checkNullEditUser(String id, String username, String password, String email) {
        return UserSetRole.checkNullEditUser(id, username, password, email);
    }

    public User findByUsername(String username) {
        return UserRepository.findByUsername(username);
    }

    //Берет любую пачку ролей и из этой пачки  делает пачку Autorities с точно такими же строками
    private Collection<? extends GrantedAuthority> mapRolesToAutorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    //Здесь из нашего User делаем UserDetails которому нужны только username, password и Autorities
    // можно посмотреть здесь https://www.youtube.com/watch?v=HvovW6Uh1yU на таймкоде 1 час 15 мин
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found: ", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAutorities(user.getRoles()));
    }

}
