package com.example.yesable_be.service;

import com.example.yesable_be.model.CoreUser;
import com.example.yesable_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


}
