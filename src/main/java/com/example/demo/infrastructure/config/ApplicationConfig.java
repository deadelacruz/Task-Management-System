package com.example.demo.infrastructure.config;

import com.example.demo.application.usecase.task.*;
import com.example.demo.application.usecase.auth.LoginUseCase;
import com.example.demo.application.usecase.auth.RegisterUseCase;
import com.example.demo.application.mapper.TaskMapper;
import com.example.demo.application.mapper.UserMapper;
import com.example.demo.domain.repository.TaskRepository;
import com.example.demo.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Application Configuration
 * Configures all beans and dependencies for the application
 */
@Configuration
public class ApplicationConfig {
    
    /**
     * Task Mapper bean
     */
    @Bean
    public TaskMapper taskMapper() {
        return new TaskMapper();
    }
    
    /**
     * User Mapper bean
     */
    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
    
    /**
     * Create Task Use Case bean
     */
    @Bean
    public CreateTaskUseCase createTaskUseCase(TaskRepository taskRepository,
                                             UserRepository userRepository,
                                             TaskMapper taskMapper) {
        return new CreateTaskUseCase(taskRepository, userRepository, taskMapper);
    }
    
    /**
     * Update Task Use Case bean
     */
    @Bean
    public UpdateTaskUseCase updateTaskUseCase(TaskRepository taskRepository,
                                             UserRepository userRepository,
                                             TaskMapper taskMapper) {
        return new UpdateTaskUseCase(taskRepository, userRepository, taskMapper);
    }
    
    /**
     * Delete Task Use Case bean
     */
    @Bean
    public DeleteTaskUseCase deleteTaskUseCase(TaskRepository taskRepository) {
        return new DeleteTaskUseCase(taskRepository);
    }
    
    /**
     * Get All Tasks Use Case bean
     */
    @Bean
    public GetAllTasksUseCase getAllTasksUseCase(TaskRepository taskRepository, TaskMapper taskMapper) {
        return new GetAllTasksUseCase(taskRepository, taskMapper);
    }
    
    /**
     * Login Use Case bean
     */
    @Bean
    public LoginUseCase loginUseCase(AuthenticationManager authenticationManager,
                                   com.example.demo.infrastructure.security.JwtTokenProvider tokenProvider,
                                   UserRepository userRepository,
                                   UserMapper userMapper) {
        return new LoginUseCase(authenticationManager, tokenProvider, userRepository, userMapper);
    }
    
    /**
     * Register Use Case bean
     */
    @Bean
    public RegisterUseCase registerUseCase(UserRepository userRepository,
                                         PasswordEncoder passwordEncoder,
                                         com.example.demo.infrastructure.security.JwtTokenProvider tokenProvider,
                                         UserMapper userMapper) {
        return new RegisterUseCase(userRepository, passwordEncoder, tokenProvider, userMapper);
    }
}
