package com.springsecurity.springsecuritydemo.controller;

import com.springsecurity.springsecuritydemo.Model.Login;
import com.springsecurity.springsecuritydemo.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import com.springsecurity.springsecuritydemo.exception.NoUserFound;
import com.springsecurity.springsecuritydemo.service.JwtService;
import com.springsecurity.springsecuritydemo.service.UserService;

@RestController
public class controller {

    Logger logger= LoggerFactory.getLogger(controller.class);
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/users")
    ResponseEntity<?> addUser(@RequestBody User u) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(u));
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody Login login) {
        System.out.println("Hello");
        logger.info("Testing the login api");
        Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(),
        login.getPassword()));
        if (authentication.isAuthenticated()) {
        System.out.println("authenticated");

        return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken(login.getUserName()));
        } else {
        return new ResponseEntity<>("No User Found",HttpStatus.BAD_REQUEST) ;
        }
    }


    @PostMapping("/checking")
    public ResponseEntity<?> checking(@RequestBody User user){
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/users")
    ResponseEntity<?> getAllusers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllusers());
    }

    @GetMapping("/users/{userName}")
    ResponseEntity<?> getuser(@PathVariable("userName") String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getuser(userName));
    }
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user")
    String user() {
        return "in user";
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    String admin() {
        return "in admin";
    }
}
