package com.springsecurity.springsecuritydemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.springsecurity.springsecuritydemo.Model.User;

public interface UserRepo extends MongoRepository<User, Integer> {
    @Query
    User findByuserName(String UserName);
}
