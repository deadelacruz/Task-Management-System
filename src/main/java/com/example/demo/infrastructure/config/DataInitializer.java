package com.example.demo.infrastructure.config;

import com.example.demo.domain.entity.Task;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.TaskRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.valueobject.Priority;
import com.example.demo.domain.valueobject.TaskStatus;
import com.example.demo.domain.valueobject.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Data Initializer
 * Initializes the application with sample data for development and testing
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public DataInitializer(UserRepository userRepository,
                          TaskRepository taskRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
        initializeTasks();
    }
    
    private void initializeUsers() {
        if (userRepository.count() == 0) {
            // Create admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setRole(UserRole.ADMIN);
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());
            userRepository.save(admin);
            
            // Create regular user
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setRole(UserRole.USER);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            
            // Create another user
            User jane = new User();
            jane.setUsername("jane");
            jane.setEmail("jane@example.com");
            jane.setPassword(passwordEncoder.encode("jane123"));
            jane.setFirstName("Jane");
            jane.setLastName("Smith");
            jane.setRole(UserRole.USER);
            jane.setCreatedAt(LocalDateTime.now());
            jane.setUpdatedAt(LocalDateTime.now());
            userRepository.save(jane);
            
            System.out.println("✅ Users initialized successfully");
        }
    }
    
    private void initializeTasks() {
        if (taskRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            User admin = users.stream().filter(u -> u.getUsername().equals("admin")).findFirst().orElse(users.get(0));
            User user = users.stream().filter(u -> u.getUsername().equals("user")).findFirst().orElse(users.get(0));
            User jane = users.stream().filter(u -> u.getUsername().equals("jane")).findFirst().orElse(users.get(0));
            
            // Create sample tasks
            List<Task> tasks = Arrays.asList(
                createTask("Complete project documentation", "Write comprehensive documentation for the new feature", 
                          TaskStatus.IN_PROGRESS, Priority.HIGH, admin, LocalDateTime.now().plusDays(5)),
                createTask("Review code changes", "Review and approve pending pull requests", 
                          TaskStatus.PENDING, Priority.MEDIUM, user, LocalDateTime.now().plusDays(2)),
                createTask("Update dependencies", "Update all project dependencies to latest versions", 
                          TaskStatus.COMPLETED, Priority.LOW, admin, LocalDateTime.now().minusDays(1)),
                createTask("Fix critical bug", "Fix the critical bug in the authentication module", 
                          TaskStatus.IN_PROGRESS, Priority.URGENT, jane, LocalDateTime.now().plusDays(1)),
                createTask("Write unit tests", "Write unit tests for the new API endpoints", 
                          TaskStatus.PENDING, Priority.MEDIUM, user, LocalDateTime.now().plusDays(3)),
                createTask("Deploy to production", "Deploy the application to production environment", 
                          TaskStatus.PENDING, Priority.HIGH, admin, LocalDateTime.now().plusDays(7)),
                createTask("User acceptance testing", "Conduct user acceptance testing with stakeholders", 
                          TaskStatus.PENDING, Priority.MEDIUM, jane, LocalDateTime.now().plusDays(4)),
                createTask("Performance optimization", "Optimize application performance for better user experience", 
                          TaskStatus.IN_PROGRESS, Priority.HIGH, user, LocalDateTime.now().plusDays(6)),
                createTask("Security audit", "Conduct security audit of the application", 
                          TaskStatus.PENDING, Priority.HIGH, admin, LocalDateTime.now().plusDays(10)),
                createTask("Database migration", "Migrate database to new schema", 
                          TaskStatus.COMPLETED, Priority.MEDIUM, user, LocalDateTime.now().minusDays(2))
            );
            
            taskRepository.saveAll(tasks);
            System.out.println("✅ Tasks initialized successfully");
        }
    }
    
    private Task createTask(String title, String description, TaskStatus status, Priority priority, User assignedUser, LocalDateTime dueDate) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(priority);
        task.setAssignedUser(assignedUser);
        task.setDueDate(dueDate);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        
        if (status == TaskStatus.COMPLETED) {
            task.setCompletedAt(LocalDateTime.now());
        }
        
        return task;
    }
}
