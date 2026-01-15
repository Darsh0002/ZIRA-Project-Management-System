package com.darsh.ZIRA_backend.repository;

import com.darsh.ZIRA_backend.modal.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptonRepo extends JpaRepository<Subscription,Long> {
    Subscription findByUserId(Long userId);
}
