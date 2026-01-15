package com.darsh.ZIRA_backend.repository;

import com.darsh.ZIRA_backend.modal.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {
    List<Message> findByIdOrderByCreatedAtAsc(Long chatId);
}
