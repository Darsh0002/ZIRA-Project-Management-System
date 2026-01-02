package com.darsh.ZIRA_backend.repository;

import com.darsh.ZIRA_backend.modal.Project;
import com.darsh.ZIRA_backend.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project,Long> {
    List<Project> findByOwner(User user);
    List<Project> findByNameContainingAndTeamContaining(String partialName,User user);

    @Query("SELECT p FROM project p JOIN p.team t WHERE t=:user")
    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User user,User owner);
}
