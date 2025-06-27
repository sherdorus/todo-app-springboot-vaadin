package io.sherdor.todoapp.service;

import io.sherdor.todoapp.entity.Todo;
import io.sherdor.todoapp.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository repository;

    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    public List<Todo> getActiveTodos() {
        return repository.findByCompletedFalseOrderByPriorityDescCompletedDesc();
    }

    public List<Todo> getCompletedTodos() {
        return repository.findByCompletedTrueAndOrderByUpdatedAtDesc();
    }

    public Optional<Todo> getById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Todo createTodo(Todo todo) {
        if (todo.getCreatedAt() == null) {
            todo.setCreatedAt(LocalDateTime.now());
        }
        return repository.save(todo);
    }

    @Transactional
    public Todo updateTodo(Todo todo) {
        todo.setUpdatedAt(LocalDateTime.now());
        return repository.save(todo);
    }

    @Transactional
    public void deleteTodo(Long id) {
        repository.deleteById(id);
    }

    public Todo markAsCompleted(Long id) {
        Optional<Todo> task = repository.findById(id);
        if (task.isPresent()) {
            var todo = task.get();
            todo.setCompleted(true);
            todo.setUpdatedAt(LocalDateTime.now());
            return repository.save(todo);
        }
        throw new IllegalArgumentException("Task with ID " + id + " not found");
    }

    public List<Todo> searchTodos(String searchItem) {
        if (searchItem == null || searchItem.trim().isEmpty()) {
            return getAllTodos();
        }
        return repository.findByTitleOrDescriptionContainingIgnoreCase(searchItem.trim());
    }

    public List<Todo> getOverdueTodos() {
        return repository.findByOverdueTodos(LocalDateTime.now());
    }

    public List<Todo> getTodayTodos() {
        return repository.findTodoForDate(LocalDateTime.now());
    }
}
