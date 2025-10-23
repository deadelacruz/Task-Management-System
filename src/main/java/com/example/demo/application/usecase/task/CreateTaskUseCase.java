package com.example.demo.application.usecase.task;

import com.example.demo.application.dto.CreateTaskRequest;
import com.example.demo.application.dto.TaskDto;
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
public class CreateTaskUseCase {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    
    public CreateTaskUseCase(TaskRepository taskRepository, 
                           UserRepository userRepository,
                           TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }
    
    public TaskDto execute(CreateTaskRequest request) {
        // Validate assigned user if provided
        User assignedUser = null;
        if (request.getAssignedUserId() != null) {
            assignedUser = userRepository.findById(request.getAssignedUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getAssignedUserId()));
        }
        
        // Create task entity
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setEstimatedHours(request.getEstimatedHours());
        task.setAssignedUser(assignedUser);
        task.setCreatedAt(java.time.LocalDateTime.now());
        task.setUpdatedAt(java.time.LocalDateTime.now());
        
        // Save task
        Task savedTask = taskRepository.save(task);
        
        // Convert to DTO
        TaskDto taskDto = taskMapper.toDto(savedTask);
        
        // Return DTO
        return taskDto;
    }
}
