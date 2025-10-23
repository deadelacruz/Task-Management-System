package com.example.demo.presentation.controller;

import com.example.demo.application.dto.CreateTaskRequest;
import com.example.demo.application.dto.TaskDto;
import com.example.demo.application.dto.UpdateTaskRequest;
import com.example.demo.application.usecase.task.*;
import com.example.demo.domain.valueobject.TaskStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetAllTasksUseCase getAllTasksUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase,
                         UpdateTaskUseCase updateTaskUseCase,
                         DeleteTaskUseCase deleteTaskUseCase,
                         GetAllTasksUseCase getAllTasksUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.getAllTasksUseCase = getAllTasksUseCase;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = getAllTasksUseCase.execute();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody CreateTaskRequest request) {
        TaskDto task = createTaskUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, 
                                            @Valid @RequestBody UpdateTaskRequest request) {
        TaskDto task = updateTaskUseCase.execute(id, request);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        deleteTaskUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDto>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<TaskDto> tasks = getAllTasksUseCase.execute(status);
        return ResponseEntity.ok(tasks);
    }
}
