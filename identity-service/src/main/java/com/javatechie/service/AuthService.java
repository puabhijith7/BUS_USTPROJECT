package com.javatechie.service;

import com.javatechie.entity.UserCredential;
import com.javatechie.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<?> saveUser(UserCredential credential) {

         Optional<UserCredential> credential1=repository.findByName(credential.getName());
        if(credential1.isPresent())
        {
            return ResponseEntity.badRequest().build();
        }
        else {
            credential.setPassword(passwordEncoder.encode(credential.getPassword()));
            repository.save(credential);
            return ResponseEntity.ok().build();
        }
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}
