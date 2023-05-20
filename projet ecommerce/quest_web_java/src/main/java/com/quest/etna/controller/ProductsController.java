package com.quest.etna.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.model.ApiResponse;
import com.quest.etna.model.User;
import com.quest.etna.model.UserRole;
import com.quest.etna.config.ProductService;
import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.Products;
import com.quest.etna.respositories.ProductRepository;
import com.quest.etna.respositories.UserRepository;

import org.apache.commons.lang3.math.NumberUtils;


@RestController
public class ProductsController {
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductService productService;

	
	@GetMapping("/products")
	public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Products> getProductById(@PathVariable("id") int id) {
		Optional<Products> optionalProduct = productService.getProductById(id);
		if (optionalProduct.isPresent()) {
			Products product = optionalProduct.get();
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@PostMapping("/products")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> products(@RequestBody Map<String, String> body, Authentication authentication){
	    try {
	        String name = body.get("name");
	        String description = body.get("description");
	        String priceStr = body.get("price");
	        //System.out.println("Price value received from request body: " + priceStr); // Nouvelle ligne
	        Double price = Double.parseDouble(priceStr);
	        int id_category = Integer.parseInt(body.get("id_category"));
	        String image = body.get("image");
	        
	        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
	        User user = userRepository.findByUsername(userDetails.getUsername());

	        if (user.getRole() != UserRole.ROLE_ADMIN) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Unauthorized"));
	        }

	        Products product = new Products(name, description, price, id_category, image);
	        productRepository.save(product);

	        return ResponseEntity.status(HttpStatus.OK).body(product);
	    } catch (NumberFormatException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid price or category ID"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Server error"));
	    }
	}

	
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody Products updatedProduct, Authentication authentication) {
	    try {
	        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
	        User user = userRepository.findByUsername(userDetails.getUsername());

	        if (user.getRole() != UserRole.ROLE_ADMIN) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Unauthorized"));
	        }
	        
	        Optional<Products> optionalProduct = productService.getProductById(id);
	        
	        if (!optionalProduct.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found"));
	        }
	        
	        Products product = optionalProduct.get();
	    
	        if (updatedProduct.getName() != null) {
	            product.setName(updatedProduct.getName());
	        }
	        if (updatedProduct.getDescription() != null) {
	            product.setDescription(updatedProduct.getDescription());
	        }
	        if (updatedProduct.getPrice() != 0) {
	            product.setPrice(updatedProduct.getPrice());
	        }
	        if (updatedProduct.getIdCategory() != 0) {
	            product.setIdCategory(updatedProduct.getIdCategory());
	        }
	        if (updatedProduct.getImage() != null) {
	            product.setImage(updatedProduct.getImage());
	        }

	        Products savedProduct = productService.saveProducts(product);
	        return ResponseEntity.status(HttpStatus.OK).body(savedProduct);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Server error"));
	    }
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable("id") int id, Authentication authentication) {
	    try {
	        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
	        User user = userRepository.findByUsername(userDetails.getUsername());

	        if (user.getRole() != UserRole.ROLE_ADMIN) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
	        }

	        Optional<Products> optionalProducts = productService.getProductById(id);
			if (optionalProducts.isPresent()) {
				productService.deleteProductsById(id);
				Map<String, Boolean> response = new HashMap<>();
				response.put("success", true);
				return ResponseEntity.ok(response);
			} else {
				Map<String, Boolean> response = new HashMap<>();
				response.put("success", false);
				return ResponseEntity.ok(response);
			}
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<>());
	    }
	}

	
	


	    



}
