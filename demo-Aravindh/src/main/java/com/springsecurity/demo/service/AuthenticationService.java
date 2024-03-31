package com.springsecurity.demo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.demo.Repository.UserRepository;
import com.springsecurity.demo.model.AuthenticationResponse;
import com.springsecurity.demo.model.User;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordencoder;
    private final JwtService jwtservice;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordencoder, JwtService jwtservice,
            AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordencoder = passwordencoder;
        this.jwtservice = jwtservice;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {
    	if(repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist");
        }

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setPassword(passwordencoder.encode(request.getPassword()));


        user.setRole(request.getRole());

        user = repository.save(user);

        String jwt = jwtservice.generateToken(user);

        return new AuthenticationResponse(jwt, "User registration was successful");

    }
    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtservice.generateToken(user);

        return new AuthenticationResponse(token,"User login was successful");
    }
}
