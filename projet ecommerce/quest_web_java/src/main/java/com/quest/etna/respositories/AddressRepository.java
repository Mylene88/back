package com.quest.etna.respositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.quest.etna.model.Address;
import com.quest.etna.model.User;

public interface AddressRepository extends CrudRepository<Address, Long >{
	
	List<Address> findAll();

	/*// Recherche d'adresses par ville
    List<Address> findByCity(String city);
    
    // Recherche d'adresses par code postal
    List<Address> findByPostal_Code(String postal_code);
    
    // Recherche d'adresses par pays et ville
    List<Address> findByCountryAndCity(String country, String city);
    */
    //Recherche d'adresses d'un utilisateur particulier
    List<Address> findByUser(User user);
    
    //Recherche d'adresses en fonction de l'id 
    Optional<Address> findById(int id);
    
    // Suppression d'une adresse en fonction de l'id
 	void deleteById(int id);
   
}
