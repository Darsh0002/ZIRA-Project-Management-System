package com.darsh.ZIRA_backend.service;

import com.darsh.ZIRA_backend.modal.Chat;
import com.darsh.ZIRA_backend.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepo chatRepo;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepo.save(chat);
    }
}
