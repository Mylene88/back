package com.quest.etna.respositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.quest.etna.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	
	List<User> findAll();
	
	Optional<User> findById(int id);
	
	void deleteById(int id);

}
