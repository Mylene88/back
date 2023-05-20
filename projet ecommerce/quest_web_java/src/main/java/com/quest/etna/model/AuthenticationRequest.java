package com.quest.etna.model;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationRequest {

    private String username;
    private String password;

    public AuthenticationRequest() {}

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters et Setters

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
