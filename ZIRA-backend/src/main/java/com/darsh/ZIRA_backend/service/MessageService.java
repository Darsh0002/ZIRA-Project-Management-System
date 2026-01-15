package com.darsh.ZIRA_backend.service;

import com.darsh.ZIRA_backend.modal.Chat;
import com.darsh.ZIRA_backend.modal.Message;
import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.repository.MessageRepo;
import com.darsh.ZIRA_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProjectService projectService;

    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepo.findById(senderId).orElseThrow(() -> new Exception("User Not found"));
        Chat chat = projectService.getChatByProjectId(projectId);

        Message msg = new Message();
        msg.setContent(content);
        msg.setChat(chat);
        msg.setSender(sender);
        msg.setCreatedAt(LocalDateTime.now());

        Message savedMessage = messageRepo.save(msg);

        chat.getMessages().add(savedMessage);
        return savedMessage;
    }

    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageRepo.findByIdOrderByCreatedAtAsc(chat.getId());
    }
}
