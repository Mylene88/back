package com.quest.etna.model;

import java.io.Serializable;
import java.util.List;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String jwtToken;
   /* private final String username;
    private final String password;
    private final List<String> roles;*/
    
    public JwtResponse(String jwtToken) {
    	this.jwtToken = jwtToken;
    
    }
    /*public JwtResponse(String jwtToken, String username, String password, List<String> roles) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }*/

    public String getToken() {
        return this.jwtToken;
    }

   /*public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public List<String> getRoles() {
        return this.roles;
    }*/
}
