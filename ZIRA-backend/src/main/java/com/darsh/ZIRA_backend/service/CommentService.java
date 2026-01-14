package com.darsh.ZIRA_backend.service;

import com.darsh.ZIRA_backend.modal.Comment;
import com.darsh.ZIRA_backend.modal.Issue;
import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.repository.CommentRepo;
import com.darsh.ZIRA_backend.repository.IssueRepo;
import com.darsh.ZIRA_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private IssueRepo issueRepo;
    @Autowired
    private UserRepo userRepo;

    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Issue issue = issueRepo.findById(issueId).orElse(null);
        User user = userRepo.findById(userId).orElse(null);

        if (issue == null || user == null) {
            throw new Exception("Error Creating Comment");
        }

        Comment comment = new Comment();

        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreatedDateTime(LocalDateTime.now());
        comment.setContent(content);

        Comment saved = commentRepo.save(comment);

        issue.getComments().add(saved);

        return saved;
    }

    public void deleteComment(Long commentId, Long userId) throws Exception {
        Comment comment = commentRepo.findById(commentId).orElse(null);
        User user = userRepo.findById(userId).orElse(null);

        if (comment == null || user == null) {
            throw new Exception("Error Deleting Comment");
        }

        if (comment.getUser().equals(user)) {
            commentRepo.delete(comment);
        } else {
            throw new Exception("User Does Not Have Permission to delete this comment");
        }
    }

    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepo.findByIssueId(issueId);
    }
}
