package com.darsh.ZIRA_backend.controller;

import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> createUserHandler(@RequestBody User user) throws Exception {
        User existingUser = userService.getUser(user.getEmail());
        if (existingUser != null) {
            throw new Exception("Email Already Exist");
        }

        User savedUser = userService.createNewUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
