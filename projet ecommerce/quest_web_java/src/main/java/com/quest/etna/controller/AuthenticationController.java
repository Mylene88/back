package com.quest.etna.controller;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.config.JwtUserDetailsService;
import com.quest.etna.model.ApiResponse;
import com.quest.etna.model.AuthenticationRequest;
import com.quest.etna.model.JwtResponse;
import com.quest.etna.model.User;
import com.quest.etna.model.UserRole;
import com.quest.etna.model.UsersDetailsCopie;
import com.quest.etna.respositories.UserRepository;


@RestController
public class AuthenticationController {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        try {
			String username = body.get("username");
			String password = body.get("password");
			if (username == null || password == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Nom d'utilisateur ou mot de passe manquant"));
	        }
			if (userRepository.findByUsername(username) != null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Nom d'utilisateur déjà utilisé"));
				//return ResponseEntity.status(HttpStatus.CONFLICT).body("Nom d'utilisateur déjà utilisé");
			}
			User newUser = new User();
			newUser.setUsername(username);
			newUser.setPassword(passwordEncoder.encode(password));
			userRepository.save(newUser);
			//System.out.println(newUser);
			 UsersDetailsCopie usersDetailsCopie = new UsersDetailsCopie(newUser.getUsername(), newUser.getRole());
			return ResponseEntity.status(HttpStatus.CREATED).body(usersDetailsCopie);
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("bad Request"));
			//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
		}
    	
    }
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest)throws Exception {
		try {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        String jwt = jwtTokenUtil.generateToken(userDetails);
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt));
		
		} catch (BadCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid username or password"));
	    } 
	
	}
	
	@GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) throws Exception{
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			//UsersDetailsCopie usersDetailsCopie = new UsersDetailsCopie(userDetails.getUsername());
			//System.out.println(usersDetailsCopie);
       // return ResponseEntity.ok(userDetails.getUsername(), userDetails.getAuthorities());
			 //System.out.println("User details: " + userDetails);
			Optional<? extends GrantedAuthority> firstAuthority = userDetails.getAuthorities().stream().findFirst();
				String authority = "";
			if (firstAuthority.isPresent()) {
			    authority = firstAuthority.map(GrantedAuthority::getAuthority).orElse(null);
			   
			}
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("username" , userDetails.getUsername(), "role", authority));
		}catch(BadCredentialsException e) {
			 System.out.println("Bad request: " + e.getMessage());
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Bad Request"));
		}catch(Exception e) {
			// System.out.println("Unauthorized request");
			 ApiResponse errorResponse = new ApiResponse("JWT invalide ou absent");
			// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("JWT invalide ou absent"));
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
	}

}

	
