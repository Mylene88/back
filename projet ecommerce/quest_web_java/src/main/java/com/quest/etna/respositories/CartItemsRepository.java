package com.quest.etna.respositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quest.etna.model.CartItems;
import com.quest.etna.model.Carts;
import com.quest.etna.model.Products;

public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
    Optional<CartItems> findByCartAndProduct(Carts cart, Products product);
    List<CartItems> findByCart(Carts cart);
}
