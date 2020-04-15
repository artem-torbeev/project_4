package com.application_server.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomerUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails userDetails = userService.findUserByEmail(s);
        if (userDetails == null){
            throw new UsernameNotFoundException("User Not Found with -> username or email : " + s);
        }
        return userDetails;
    }
}
