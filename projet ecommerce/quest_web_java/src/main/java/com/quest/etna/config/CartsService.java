package com.quest.etna.config;


import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quest.etna.model.CartItems;
import com.quest.etna.model.Carts;
import com.quest.etna.model.Products;
import com.quest.etna.model.User;
import com.quest.etna.respositories.CartItemsRepository;
import com.quest.etna.respositories.CartsRepository;
import com.quest.etna.respositories.UserRepository;
import com.quest.etna.respositories.ProductRepository;
import org.hibernate.Hibernate;

@Service
public class CartsService {
	private static final Logger logger = LoggerFactory.getLogger(CartsService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartsRepository cartRepository;
    
    @Autowired
    private CartItemsRepository cartItemsRepository;

    public Carts createCartForUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Carts cart = new Carts();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
    

    //celui là est ok!!!
    public Carts getCartByUser(User user) {
        List<Carts> carts = cartRepository.findByUser(user);
        if (carts.isEmpty()) {
            return null;
        } else {
            return carts.get(0);
        }
    }  
   
    public CartItems getCartItemById(int cartItemId) {
        return cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));
    }



   
   public CartItems getCartItemByCartAndProduct(Carts cart, Products product) {
	    List<CartItems> cartItems = cartItemsRepository.findByCart(cart);
	    for (CartItems cartItem : cartItems) {
	        if (cartItem.getProduct().getId() == product.getId()) {
	            return cartItem;
	        }
	    }
	    return null;
	}





    public Carts saveCart(Carts cart) {
        return cartRepository.save(cart);
    }

    public void deleteCart(Carts cart) {
        cartRepository.delete(cart);
    }
    
    public void createCartItem(CartItems cartItem) {
        cartItemsRepository.save(cartItem);
    }
    
    public void updateCartItem(CartItems cartItem) {
        cartItemsRepository.save(cartItem);
    }
    
    public void deleteCartItem(CartItems cartItem) {
        cartItemsRepository.delete(cartItem);
    }


  /*  public void addProductToCart(int cartId, int productId, int quantity) {
        Carts cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart not found"));
        Products product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);
    } */
}
