package com.quest.etna.respositories;

import com.quest.etna.model.Carts;
import com.quest.etna.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartsRepository extends JpaRepository<Carts, Integer> {
    List<Carts> findByUser(User user);
    
}

