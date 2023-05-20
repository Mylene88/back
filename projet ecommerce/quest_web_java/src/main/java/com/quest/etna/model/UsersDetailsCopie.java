package com.quest.etna.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UsersDetailsCopie {
	
	private String username;
	private UserRole role;
	
	public UsersDetailsCopie(String username, Collection<? extends GrantedAuthority> collection) {
		this.username = username;
		
	}
	
	public UsersDetailsCopie(String username, UserRole role) {
		this.username = username;
		this.role = role;
	}
	

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setPassword(String password) {
		// TODO Auto-generated method stub
		
	}	


}
