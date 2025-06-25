package io.sherdor.todoapp.repositories;

import io.sherdor.todoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCompletedFalseOrderByPriorityDescCompletedDesc();

    List<Todo> findByCompletedTrueAndOrderByUpdatedAtDesc();
}
