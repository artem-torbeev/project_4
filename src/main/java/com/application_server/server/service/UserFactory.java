package com.application_server.server.service;

import com.application_server.server.model.Role;
import com.application_server.server.model.User;
import com.application_server.server.model.UserDto;
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
    private List<String> getListAuthority (List<Role> userRole){
        return userRole.stream().map(Role::getRole).collect(Collectors.toList());
    }
}
