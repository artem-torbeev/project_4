package com.application_server.server.service;

import com.application_server.server.model.Role;
import com.application_server.server.model.User;
import com.application_server.server.model.UserForm;
import com.application_server.server.repository.RoleRepository;
import com.application_server.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    //TODO
    public User addUser(UserForm userForm) {
        User user = new User(userForm.getUsername(),
                             userForm.getEmail(),
                             passwordEncoder.encode(userForm.getPassword()),
                             getSetRole(userForm.getRole()));

       return userRepository.save(user);
    }

    //TODO
    public User updateUserById(Long id, UserForm userForm) {
        User oldUser = userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        oldUser.setUsername(userForm.getUsername());
        oldUser.setEmail(userForm.getEmail());
        oldUser.setRole(getSetRole(userForm.getRole()));

        return  userRepository.save(oldUser);
    }

    protected Set<Role> getSetRole(String role) {
        Set<Role> roleSet = new HashSet<>();
        long idRole;
        if (role.equals("ROLE_ADMIN")) {
            idRole = 2L;
        } else {
            idRole = 1L;
        }
        Role roleName = roleRepository.findRoleById(idRole)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role"));

        roleSet.add(roleName);
        return roleSet;
    }

}
