package com.quest.etna.model;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class Authorization {
	
	  public boolean isAuthorized(Principal principal, String username, Collection<? extends GrantedAuthority> authorities) {
	        if (principal == null) {
	            return false;
	        }

	        String currentUser = principal.getName();
	        if (currentUser.equals(username)) {
	            return true;
	        }

	        // Vérification des rôles pour l'utilisateur ADMIN
	        boolean isAdmin = authorities.stream()
	                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

	        return isAdmin;
	    }

	  public static boolean isAuthorized(Long userId, int addressUserId) {
	        // If the user ID matches the address's user ID, then the user is authorized to create the address
	        return userId.equals((long) addressUserId);
	    }

	public static boolean isAuthorized(Long userId, int id, Authentication authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}
