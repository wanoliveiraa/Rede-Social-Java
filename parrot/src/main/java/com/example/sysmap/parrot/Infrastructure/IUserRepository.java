package com.example.sysmap.parrot.Infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.sysmap.parrot.Damon.Entities.User;

public interface IUserRepository extends MongoRepository<User, UUID> {

   public Optional<User> findByEmail(String email);
  
   public User getByEmail(String email);
   public User getByUsername(String username);
    
}
