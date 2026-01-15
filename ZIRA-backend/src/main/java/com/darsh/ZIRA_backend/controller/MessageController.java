package com.darsh.ZIRA_backend.controller;

import com.darsh.ZIRA_backend.dto.MessageRequest;
import com.darsh.ZIRA_backend.modal.Chat;
import com.darsh.ZIRA_backend.modal.Message;
import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.service.MessageService;
import com.darsh.ZIRA_backend.service.ProjectService;
import com.darsh.ZIRA_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessageByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest req) throws Exception {
        User user = userService.findUserById(req.getSenderId());

        Chat chat = projectService.getChatByProjectId(req.getProjectId());
        if (chat==null) throw new Exception("Chat Not Found");

        Message sentMessage = messageService.sendMessage(req.getSenderId(), req.getProjectId(), req.getContent());
        return ResponseEntity.ok(sentMessage);
    }
}
