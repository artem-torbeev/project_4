package com.application_server.server.model;

import java.util.Map;

public class UserJwt {

    private String email;
    private String password;
    private Map<String, Object> model;

    public UserJwt(){}

    public UserJwt(Map<String, Object> model) {
        this.model = model;
    }

    public UserJwt(String email, Map<String, Object> model) {
        this.email = email;
        this.model = model;
    }

    public UserJwt(String email, String password, Map<String, Object> model) {
        this.email = email;
        this.password = password;
        this.model = model;
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

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
