package com.darsh.ZIRA_backend.repository;

import com.darsh.ZIRA_backend.modal.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepo extends JpaRepository<Issue,Long> {
    public List<Issue> findByProjectId(Long id);
}
