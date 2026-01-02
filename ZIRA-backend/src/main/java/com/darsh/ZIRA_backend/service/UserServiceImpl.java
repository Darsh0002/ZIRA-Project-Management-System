package com.darsh.ZIRA_backend.service;

import com.darsh.ZIRA_backend.config.JwtProvider;
import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        if (user == null)
            throw new Exception("User Not Found");
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        return userRepo.findById(userId).orElseThrow();
    }

    @Override
    public User updateUsersProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize() + number);
        return userRepo.save(user);
    }
}
