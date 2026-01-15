package com.darsh.ZIRA_backend.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Long issueId;
    private String content;
}
