package com.application_server.server.service;

import com.application_server.server.model.Role;
import com.application_server.server.model.User;
import com.application_server.server.model.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFactory {

    public UserFactory() {
    }

    public UserDto create(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                getListAuthority(new ArrayList<>(user.getRole()))
        );
    }

    private List<GrantedAuthority> getListAuthority(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getRole())
                ).collect(Collectors.toList());
    }
}
