package com.application_server.server.service;

import com.application_server.server.model.Role;
import com.application_server.server.model.User;
import com.application_server.server.repository.RoleRepository;
import com.application_server.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements CustomService<User> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
    }

//TODO присваивать роль по имени
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        Long idRole;
//        if (user.getRole().contains("ROLE_ADMIN")) {
//            idRole = 2L;
//        } else {
//            idRole = 1L;
//        }
        Role role = roleRepository.findRoleById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role"));
        user.getRole().add(role);
        userRepository.save(user);
    }
//TODO
    public void updateUserById(Long id, User user) {
        User oldUser = userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        userRepository.save(oldUser);
    }

}
