package com.application_server.server.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class UserDto {
    private long id;

    private String username;

    private String email;

    private String password;

    private List<GrantedAuthority> role;

    public UserDto(long id, String username, String email, String password, List<GrantedAuthority> role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDto(String username, String email, String password, List<GrantedAuthority> role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GrantedAuthority> getRole() {
        return role;
    }

    public void setRole(List<GrantedAuthority> role) {
        this.role = role;
    }
}
