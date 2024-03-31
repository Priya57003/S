package com.springsecurity.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.demo.Repository.UserRepository;
import com.springsecurity.demo.model.User;

@RestController
public class AuthorizedController {
    private final UserRepository repository;

    @Autowired
    public AuthorizedController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> users() {
        List<User> userList = repository.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<User> userid(@PathVariable int id){
    	User user=repository.findById(id);
    	return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PostMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        repository.save(user);  // Assuming you have a save method in your repository
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
