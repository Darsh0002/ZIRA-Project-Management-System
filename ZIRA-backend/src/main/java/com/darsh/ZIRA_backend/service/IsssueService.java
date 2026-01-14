package com.darsh.ZIRA_backend.service;

import com.darsh.ZIRA_backend.dto.IssueRequest;
import com.darsh.ZIRA_backend.modal.Issue;
import com.darsh.ZIRA_backend.modal.Project;
import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.repository.IssueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IsssueService {
    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    public Issue getIssueById(Long issueId) throws Exception {
        return issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("No Issue Found With Id: " + issueId));
    }

    public List<Issue> getIssueByProjectId(Long projectId) {
        return issueRepo.findByProjectId(projectId);
    }

    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectId());

        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setProject(project);
        issue.setProjectId(issue.getProjectId());
        issue.setPriority(issue.getPriority());
        issue.setDueDate(issueRequest.getDueDate());

        return issueRepo.save(issue);
    }

    public void deleteIssue(Long issueId, Long userId) throws Exception {
        getIssueById(issueId);
        issueRepo.deleteById(issueId);
    }

    public Issue addUserToIssue(Long issueId,Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);
        issue.setAssignee(user);

        return issueRepo.save(issue);
    }

    public Issue updateStatus(Long issueId,String status) throws Exception {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepo.save(issue);
    }
}
