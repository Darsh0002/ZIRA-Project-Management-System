package com.darsh.ZIRA_backend.repository;

import com.darsh.ZIRA_backend.modal.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo extends JpaRepository<Chat,Long> {
}
