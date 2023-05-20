package com.quest.etna.respositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.quest.etna.model.Products;
import com.quest.etna.model.User;

public interface ProductRepository extends CrudRepository<Products, Long >{
	
	List<Products> findAll();

	// Recherche d'un produit par nom
    List<Products> findByName(String name);
    
    
    //Recherche d'un produit en fonction de l'id 
    Optional<Products> findById(int id);
    
    // Suppression d'un produit en fonction de l'id
 	void deleteById(int id);
   
}
