package com.darsh.ZIRA_backend.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private Long senderId;
    private String content;
    private Long projectId;
}
