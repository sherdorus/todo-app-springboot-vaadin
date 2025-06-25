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
}
