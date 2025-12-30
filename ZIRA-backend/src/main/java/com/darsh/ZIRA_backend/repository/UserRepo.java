package com.darsh.ZIRA_backend.repository;

import com.darsh.ZIRA_backend.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    public User findByEmail(String email);
}
