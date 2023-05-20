package com.quest.etna.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.model.Address;
import com.quest.etna.model.ApiResponse;
import com.quest.etna.model.User;
import com.quest.etna.model.UserRole;
import com.quest.etna.config.AddressService;
import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.respositories.AddressRepository;
import com.quest.etna.respositories.UserRepository;

@RestController
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressService addressService;

	@PostMapping("/address")
	public ResponseEntity<?> address(@RequestBody Map<String, String> body, Authentication authentication)
			throws Exception {
		try {
			String street = body.get("street");
			String postalCode = body.get("postalCode");
			String city = body.get("city");
			String country = body.get("country");

			JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

			User user = userRepository.findByUsername(userDetails.getUsername());
			Address address = new Address(street, postalCode, city, country);
			address.setUser(user);
			addressRepository.save(address);
			//System.out.println(user);

		//return new ResponseEntity<>(address, HttpStatus.CREATED).body(new ApiResponse("a new address created"));
			return ResponseEntity.status(HttpStatus.OK).body(address);
		} catch (Exception e) {
			System.out.println(e);
			// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Server error"));

		}
	}

	@GetMapping("/address")
	// public ResponseEntity<List<Address>> getAllAddresses() throws Exception {
	public ResponseEntity<?> getAllAddresses(Authentication authentication) throws Exception {
		try {
			JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

			User user = userRepository.findByUsername(userDetails.getUsername());
			
			List<Address> addresses;
			
			if(user.getRole() == UserRole.ROLE_USER) {
				addresses = addressService.getAddressByUser(user);
			}else {
				addresses = addressService.getAllAddresses();
			}
			

			// return new ResponseEntity<>(addresses, HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body(addresses);

		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Server error"));
		}
	}

	@GetMapping("/address/{id}/")
	public ResponseEntity<Address> getAddressById(@PathVariable("id") int id) {
		Optional<Address> optionalAddress = addressService.getAddressById(id);
		if (optionalAddress.isPresent()) {
			Address address = optionalAddress.get();
			return new ResponseEntity<>(address, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/address/{id}")
	public ResponseEntity<Address> updateAddress(@PathVariable("id") int id, @RequestBody Address updatedAddress) {
		Optional<Address> optionalAddress = addressService.getAddressById(id);
		if (optionalAddress.isPresent()) {
			Address address = optionalAddress.get();
			if (updatedAddress.getStreet() != null) {
				address.setStreet(updatedAddress.getStreet());
			}
			if (updatedAddress.getCity() != null) {
				address.setCity(updatedAddress.getCity());
			}
			if (updatedAddress.getPostalCode() != null) {
				address.setPostalCode(updatedAddress.getPostalCode());
			}
			if (updatedAddress.getCountry() != null) {
				address.setCountry(updatedAddress.getCountry());
			}
			Address savedAddress = addressService.saveAddress(address);
			return new ResponseEntity<>(savedAddress, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/address/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteAddress(@PathVariable("id") int id) {
		Optional<Address> optionalAddress = addressService.getAddressById(id);
		if (optionalAddress.isPresent()) {
			addressService.deleteAddressById(id);
			Map<String, Boolean> response = new HashMap<>();
			response.put("success", true);
			return ResponseEntity.ok(response);
		} else {
			Map<String, Boolean> response = new HashMap<>();
			response.put("success", false);
			return ResponseEntity.ok(response);
		}
	}
}
