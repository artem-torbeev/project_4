package com.application_server.server.model;

import java.util.List;

public class UserDto {
    private long id;

    private String username;

    private String email;

    private String password;

    private List<String> role;

    public UserDto(long id, String username, String email, String password, List<String> role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserDto(String username, String email, String password, List<String> role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserDto() {
    }

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
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

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
