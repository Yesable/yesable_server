package com.example.yesable_be.controller;

import com.example.yesable_be.model.CoreUser;
import com.example.yesable_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody CoreUser user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
