package com.darsh.ZIRA_backend.service;

import com.darsh.ZIRA_backend.modal.Chat;
import com.darsh.ZIRA_backend.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService{

    @Autowired
    private ChatRepo chatRepo;

    public Chat createChat(Chat chat) {
        return chatRepo.save(chat);
    }
}
