package com.example.demo.domain.repository;

import com.example.demo.domain.entity.Task;
import com.example.demo.domain.valueobject.Priority;
import com.example.demo.domain.valueobject.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Find by status
    List<Task> findByStatus(TaskStatus status);
    
    // Find by priority
    List<Task> findByPriority(Priority priority);
    
    // Find by user
    List<Task> findByAssignedUser_Id(Long userId);
    
    // Find overdue tasks
    @Query("SELECT t FROM Task t WHERE t.dueDate < :now AND t.status != 'COMPLETED'")
    List<Task> findOverdueTasks(@Param("now") LocalDateTime now);
    
    
    // Find tasks by multiple criteria
    @Query("SELECT t FROM Task t WHERE " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:priority IS NULL OR t.priority = :priority) AND " +
           "(:userId IS NULL OR t.assignedUser.id = :userId)")
    Page<Task> findByCriteria(@Param("status") TaskStatus status,
                             @Param("priority") Priority priority,
                             @Param("userId") Long userId,
                             Pageable pageable);
    
    
    // Find tasks due soon (within next N days)
    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :now AND :futureDate AND t.status != 'COMPLETED'")
    List<Task> findTasksDueSoon(@Param("now") LocalDateTime now, 
                               @Param("futureDate") LocalDateTime futureDate);
}
