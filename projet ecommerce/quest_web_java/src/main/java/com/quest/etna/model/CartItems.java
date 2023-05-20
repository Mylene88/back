package com.quest.etna.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.quest.etna.model.Products;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quest.etna.model.Carts;

@Entity
@Table(name = "cart_items")
public class CartItems {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "id_cart", nullable = false)
    @JsonIgnore // pour ignorer la sérialisation de la propriété cart
    private Carts cart;
    
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Products product;
    
    private int quantity;
    
    // Constructeur par défaut
    public CartItems() {}

 // Constructeur avec paramètres
    public CartItems(Carts cart, Products product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }


    


    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Carts getCart() {
        return cart;
    }

    public void setCart(Carts cart) {
        this.cart = cart;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

