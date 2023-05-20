package com.quest.etna.model;

public class UserDetails {
	
	private String username;
	private UserRole role;
	
	public UserDetails() {}
	
	public UserDetails(String username, UserRole role) {
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

	public Object getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
