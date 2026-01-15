package com.darsh.ZIRA_backend.service;

import com.darsh.ZIRA_backend.modal.Chat;
import com.darsh.ZIRA_backend.modal.Project;
import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    public Project createProject(Project project, User user) throws Exception {
        Project created = new Project();
        created.setOwner(user);
        created.setTags(project.getTags());
        created.setName(project.getName());
        created.setCategory(project.getCategory());
        created.setDescription(project.getDescription());
        created.getTeam().add(user);

        Project savedProject = projectRepo.save(created);

        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepo.findByTeamContainingOrOwner(user, user);

        if (category != null) {
            projects = projects.stream().filter(project -> project.getCategory().equals(category)).toList();
        }
        if (tag != null) {
            projects = projects.stream().filter(project -> project.getTags().contains(tag)).toList();
        }
        return projects;
    }

    public Project getProjectById(Long projectId) throws Exception {
        return projectRepo.findById(projectId).orElseThrow(() -> new Exception("Project with ID: " + projectId + " Not Found"));
    }

    public void deleteProject(Long projectId, Long userId) throws Exception {
        projectRepo.deleteById(projectId);
    }

    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepo.save(project);
    }

    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);

        User userToAdd = userService.findUserById(userId);

        if(!project.getTeam().contains(userToAdd)){
            project.getChat().getUsers().add(userToAdd);
            project.getTeam().add(userToAdd);
        }
        projectRepo.save(project);
    }

    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);

        User userToRemove = userService.findUserById(userId);

        if(project.getTeam().contains(userToRemove)){
            project.getChat().getUsers().remove(userToRemove);
            project.getTeam().remove(userToRemove);
        }
        projectRepo.save(project);
    }

    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    public List<Project> searchProject(String keyword, User user) throws Exception {
        return projectRepo.findByNameContainingAndTeamContaining(keyword, user);
    }
}
