package com.vaultnote.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaultnote.api.dto.RegisterRequestDto;
import com.vaultnote.api.dto.LoginRequestDto;
import com.vaultnote.api.model.User;
import com.vaultnote.api.service.UserService;

@RestController 
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    //Dependency Injection via constructor
    public AuthController(UserService userService){
        this.userService = userService;
    }

    //1.Register Endpoint: Handles account registration/creation request
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto request){

        try{
            //Ask our service to create the account (it will hash the password)
            User newUser = userService.registerUser(request.getEmail(), request.getPassword());

            //Return a successful response with the newly created user
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("userId", newUser.getId().toString());

            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e){
            //If email is taken, return an HTTP 400 Bad Request with the error message
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    //2. Login Endpoint: Checks credentials & issues JWT Token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request){
        // Implement login logic here
    }

    
}
