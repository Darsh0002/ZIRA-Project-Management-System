package com.darsh.ZIRA_backend.service;

import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsImpl customUserDetails;

    public User createNewUser(User user) {
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        return userRepo.save(newUser);
    }

    public User getUser(String email){
        return userRepo.findByEmail(email);
    }
}
