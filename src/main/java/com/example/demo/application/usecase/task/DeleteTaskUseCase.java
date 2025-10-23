package com.example.demo.application.usecase.task;

import com.example.demo.domain.repository.TaskRepository;
import com.example.demo.infrastructure.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteTaskUseCase {
    
    private final TaskRepository taskRepository;
    
    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    public void execute(Long taskId) {
        // Check if task exists
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }
        
        // Delete task
        taskRepository.deleteById(taskId);
    }
}
