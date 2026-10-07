package com.vaultnote.api.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vaultnote.api.model.User;
import com.vaultnote.api.repository.UserRepository;

@Service 
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //1. Register a new user
    public User registerUser(String email, String rawPassword){

        //check if the user with this email already exists in the database
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email is already taken!");
        }

        User newUser = new User();

        newUser.setEmail(email);

        //Scramble the password before saving it
        String hashedPassword = passwordEncoder.encode(rawPassword);
        newUser.setPasswordHash(hashedPassword);

        return userRepository.save(newUser);

    }

    //2. Authenticate / Login User
    //Returns a signed JWT token if the credentials are correct, otherwise throws an error
    public String loginUser(String email, String rawPassword, JwtService jwtService){
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()){
            throw new RuntimeException("Invalid email or password!");
        }

        User user = userOpt.get();

        if(!passwordEncoder.matches(rawPassword, user.getPasswordHash())){
            throw new RuntimeException("Invalid email or password!");
        }

        return jwtService.generateToken(user.getEmail());
    }

    
}
