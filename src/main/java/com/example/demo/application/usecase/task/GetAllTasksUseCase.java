package com.example.demo.application.usecase.task;

import com.example.demo.application.dto.TaskDto;
import com.example.demo.application.mapper.TaskMapper;
import com.example.demo.domain.entity.Task;
import com.example.demo.domain.repository.TaskRepository;
import com.example.demo.domain.valueobject.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetAllTasksUseCase {
    
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    
    public GetAllTasksUseCase(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }
    
    public List<TaskDto> execute() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.toDtoList(tasks);
    }
    
    public Page<TaskDto> execute(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        return tasks.map(taskMapper::toDto);
    }
    
    public List<TaskDto> execute(TaskStatus status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        return taskMapper.toDtoList(tasks);
    }
}
