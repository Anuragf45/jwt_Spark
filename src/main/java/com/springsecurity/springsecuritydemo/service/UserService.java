package com.springsecurity.springsecuritydemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.springsecuritydemo.Model.User;
import com.springsecurity.springsecuritydemo.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    UserRepo db;
    @Autowired
    PasswordEncoder pe;

    public User addUser(User u) {
        u.setPassword(pe.encode(u.getPassword()));
        return db.save(u);
    }

    public List<User> getAllusers() {
        return db.findAll();
    }

    public User getuser(String userName) {
        return db.findByuserName(userName);
    }
}
