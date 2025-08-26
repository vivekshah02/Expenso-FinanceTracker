package com.financetracker.repository;

import com.financetracker.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    // finding user by email for login and reg
    Optional<User> findByEmail(String email);
    
    //chhecking if email already exists 
    boolean existsByEmail(String email);
}