package com.darsh.ZIRA_backend.controller;

import com.darsh.ZIRA_backend.config.JwtProvider;
import com.darsh.ZIRA_backend.dto.AuthResponse;
import com.darsh.ZIRA_backend.dto.LoginRequest;
import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) throws Exception {
        User existingUser = authService.getUser(user.getEmail());
        if (existingUser != null) {
            return new ResponseEntity<>("Email Already Exists",HttpStatus.BAD_REQUEST);
        }

        User savedUser = authService.createNewUser(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse("SignUp Successful", jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest req){
        String email=req.getEmail();
        String password=req.getPassword();

        Authentication authentication = authService.authenticateUser(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse("Login Successful", jwt);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
