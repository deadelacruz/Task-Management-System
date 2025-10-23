package com.example.demo.application.usecase.task;

import com.example.demo.application.dto.TaskDto;
import com.example.demo.application.dto.UpdateTaskRequest;
import com.example.demo.application.mapper.TaskMapper;
import com.example.demo.domain.entity.Task;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.TaskRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateTaskUseCase {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    
    public UpdateTaskUseCase(TaskRepository taskRepository, 
                           UserRepository userRepository,
                           TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }
    
    public TaskDto execute(Long taskId, UpdateTaskRequest request) {
        // Find existing task
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        
        // Update fields if provided
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }
        if (request.getEstimatedHours() != null) {
            task.setEstimatedHours(request.getEstimatedHours());
        }
        if (request.getActualHours() != null) {
            task.setActualHours(request.getActualHours());
        }
        if (request.getAssignedUserId() != null) {
            User assignedUser = userRepository.findById(request.getAssignedUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getAssignedUserId()));
            task.setAssignedUser(assignedUser);
        }
        
        // Update the updatedAt field
        task.setUpdatedAt(java.time.LocalDateTime.now());
        
        // Save updated task
        Task updatedTask = taskRepository.save(task);
        
        // Convert to DTO
        TaskDto taskDto = taskMapper.toDto(updatedTask);
        
        // Return DTO
        return taskDto;
    }
}
