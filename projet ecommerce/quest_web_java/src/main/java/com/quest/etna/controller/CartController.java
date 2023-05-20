package com.quest.etna.controller;

import com.quest.etna.model.CartItems;
import com.quest.etna.model.Carts;
import com.quest.etna.model.Products;
import com.quest.etna.model.User;
//import com.quest.etna.repositories.CartItemsRepository;
import com.quest.etna.respositories.CartsRepository;
import com.quest.etna.respositories.ProductRepository;
import com.quest.etna.respositories.UserRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.quest.etna.config.CartsService;
import com.quest.etna.config.UserService;
import com.quest.etna.model.JwtUserDetails;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
//@RequestMapping(path = "/cart")
public class CartController {

    @Autowired
    private CartsRepository cartsRepository;

    @Autowired
    private ProductRepository productsRepository;
    
    @Autowired
    private UserRepository userRepository;

    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CartsService cartsService;
    
    @Autowired
    private CartsService productService;


    
    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItems cartItems, Authentication authentication) {
        // Récupération de l'utilisateur courant
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        // Récupération du panier de l'utilisateur
        Carts cart = cartsService.getCartByUser(user);

        // Récupération du produit à ajouter
        Products product = productsRepository.findById(cartItems.getProduct().getId()).orElse(null);

        // Vérification si le produit existe déjà dans le panier
        CartItems existingCartItem = cartsService.getCartItemByCartAndProduct(cart, product);

 
        if (existingCartItem == null || existingCartItem.getCart().getId() != cart.getId() || existingCartItem.getProduct().getId() != product.getId()) {
            // ...

            // Si le produit n'existe pas encore dans le panier ou les ID ne correspondent pas, on le crée
            CartItems newCartItem = new CartItems();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(cartItems.getQuantity());
            cartsService.createCartItem(newCartItem);
            return ResponseEntity.ok("Produit ajouté au panier avec succès.");
        } else {
            // Si le produit existe déjà dans le panier, on met à jour la quantité
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItems.getQuantity());
            cartsService.updateCartItem(existingCartItem);
            return ResponseEntity.ok("Quantité du produit mise à jour avec succès.");
        }
    }
    
   /* @GetMapping("/cart/items")
    
    public ResponseEntity<List<CartItems>> getCartItems(Authentication authentication) {
        // Récupération de l'utilisateur courant
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        // Récupération du panier de l'utilisateur
        Carts cart = cartsService.getCartByUser(user);

        // Récupération des éléments du panier
        List<CartItems> cartItems = cart.getCartItems();

        return ResponseEntity.ok(cartItems);
    } */
    
    @GetMapping("/cart/items")
    public ResponseEntity<List<CartItems>> getCartItems(Authentication authentication) {
        // Récupération de l'utilisateur courant
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        // Récupération du panier de l'utilisateur
        Carts cart = cartsService.getCartByUser(user);

        // Récupération des éléments du panier
        List<CartItems> cartItems;
        
        if (cart != null) {
            cartItems = cart.getCartItems();
            cartItems.forEach(cartItem -> Hibernate.initialize(cartItem));
        } else {
            cartItems = Collections.emptyList();
        }

        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/cart/updateQuantity/{itemId}")
    public ResponseEntity<String> updateCartItemQuantity(@PathVariable("itemId") int itemId, @RequestBody Map<String, Integer> request, Authentication authentication) {
    	
    	int quantity = request.get("quantity");
        // Récupération de l'utilisateur courant
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        // Récupération du panier de l'utilisateur
        Carts cart = cartsService.getCartByUser(user);

        // Récupération de l'élément du panier à mettre à jour
        CartItems cartItem = cartsService.getCartItemById(itemId);

        if (cartItem == null || cartItem.getCart().getId() != cart.getId()) {
            return ResponseEntity.ok("L'élément du panier n'existe pas.");
        }

        if (quantity < 1) {
            // Si la quantité est inférieure à 1, on supprime l'élément du panier
            cartsService.deleteCartItem(cartItem);
            return ResponseEntity.ok("L'élément du panier a été supprimé.");
        } else {
            // Mise à jour de la quantité de l'élément du panier
            cartItem.setQuantity(quantity);
            cartsService.updateCartItem(cartItem);
            return ResponseEntity.ok("La quantité de l'élément du panier a été mise à jour.");
        }
    }



    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable("id") int productId, Authentication authentication) {
        // Récupération de l'utilisateur courant
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        // Récupération du panier de l'utilisateur
        Carts cart = cartsService.getCartByUser(user);

        // Récupération du produit à supprimer
        Products product = productsRepository.findById(productId).orElse(null);

        // Vérification si le produit existe dans le panier
        CartItems existingCartItem = cartsService.getCartItemByCartAndProduct(cart, product);

        if (existingCartItem != null) {
            cartsService.deleteCartItem(existingCartItem);
            return ResponseEntity.ok("Produit supprimé du panier avec succès.");
        } else {
            return ResponseEntity.ok("Le produit n'existe pas dans le panier.");
        }
    }



}

