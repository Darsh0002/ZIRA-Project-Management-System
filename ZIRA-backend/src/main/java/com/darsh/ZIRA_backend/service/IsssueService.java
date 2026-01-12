package com.darsh.ZIRA_backend.service;

import com.darsh.ZIRA_backend.modal.Issue;
import com.darsh.ZIRA_backend.repository.IssueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IsssueService {
    @Autowired
    private IssueRepo issueRepo;

    public Issue getIssueById(Long issueId) throws Exception {
        return issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("No Issue Found With Id: " + issueId));
    }

    public List<Issue> getIssueByProjectId(Long projectId){
        return issueRepo.findByProjectId(projectId);
    }
}
