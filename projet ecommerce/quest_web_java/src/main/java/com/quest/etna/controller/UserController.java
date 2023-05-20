package com.quest.etna.controller;

import com.quest.etna.config.UserService;
import com.quest.etna.model.Address;
import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.model.UserRole;
import com.quest.etna.respositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@RestController
//@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User updatedUser, Authentication authentication) {
        Optional<User> optionalUser = userService.getUserById(id);
        
        if (optionalUser.isPresent()) {
        	
        	JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

    		User userConnected = userRepository.findByUsername(userDetails.getUsername());
    		
    		if(userConnected.getRole().equals(UserRole.ROLE_USER)) {
    			if(userConnected.getId() == optionalUser.get().getId()) {
    	            if (updatedUser.getUsername() != null) {
    	            	optionalUser.get().setUsername(updatedUser.getUsername());
    	            }
    			}else {
    				return ResponseEntity.badRequest().build();
    			}
    		}else {
    			if (updatedUser.getUsername() != null) {
	            	optionalUser.get().setUsername(updatedUser.getUsername());
	            }
    			if (updatedUser.getRole() != null) {
    				optionalUser.get().setRole(updatedUser.getRole());
                }
    		}
        	
            userService.saveUser(optionalUser.get());
            return ResponseEntity.ok().body(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUserById(@PathVariable("id") int id, Authentication authentication) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
        	JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

    		User userConnected = userRepository.findByUsername(userDetails.getUsername());
    		
    		if(userConnected.getRole().equals(UserRole.ROLE_USER)) {
    			if(userConnected.getId() == optionalUser.get().getId()) {
    				userService.deleteUserById(id);
    	            Map<String, Boolean> response = new HashMap<>();
    	            response.put("success", true);
    	            return ResponseEntity.ok(response);
    			}else {
    				return ResponseEntity.badRequest().build();
    			}
    		}else {
    			userService.deleteUserById(id);
	            Map<String, Boolean> response = new HashMap<>();
	            response.put("success", true);
	            return ResponseEntity.ok(response);
    		}
        	
        } else {
            Map<String, Boolean> response = new HashMap<>();
            response.put("success", false);
            return ResponseEntity.ok(response);
        }
    }


}
