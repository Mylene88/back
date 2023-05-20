package com.quest.etna.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quest.etna.model.Products;
import com.quest.etna.respositories.ProductRepository;


@Service
public class ProductService {
	
	@Autowired
    private ProductRepository productRepository;
	
	public List<Products> getAllProducts() {
        return productRepository.findAll();
    }
	
	public Optional<Products> getProductById(int id) {
    	return productRepository.findById(id);
    }

	public List<Products> getProductByName(String name) {
    	return productRepository.findByName(name);
    }
	
	public Products createProducts(Products products) {
        return productRepository.save(products);
    }
    
    public Products saveProducts(Products products) {
        return productRepository.save(products);
    }
    @Transactional
    public boolean deleteProductsById(int id) {
        Optional<Products> optionalProducts = productRepository.findById(id);
        if (optionalProducts.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
