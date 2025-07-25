package io.sherdor.todoapp.repositories;

import io.sherdor.todoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCompletedFalseOrderByPriorityDescCompletedDesc();

    List<Todo> findByCompletedTrueOrderByUpdatedAtDesc();

    @Query("SELECT t FROM Todo t WHERE " +
           "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Todo> findByTitleOrDescriptionContainingIgnoreCase(@Param("searchTerm") String searchTerm);

    @Query("select t from Todo t where t.dueDate < :now and t.completed=false")
    List<Todo> findByOverdueTodos(@Param("now") LocalDateTime now);

    @Query("select t from Todo t where DATE(t.dueDate) = DATE(:date) and t.completed=false")
    List<Todo> findTodoForDate(@Param("date") LocalDateTime date);

    long countByCompletedTrue();

    long countByCompletedFalse();
}
