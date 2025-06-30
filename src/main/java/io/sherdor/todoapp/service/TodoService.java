package io.sherdor.todoapp.service;

import io.sherdor.todoapp.config.TodoStats;
import io.sherdor.todoapp.dto.CreateTodoDto;
import io.sherdor.todoapp.dto.TodoDto;
import io.sherdor.todoapp.dto.UpdateTodoDto;
import io.sherdor.todoapp.mappers.TodoMapper;
import io.sherdor.todoapp.repositories.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final TodoRepository repository;
    private final TodoMapper mapper;

    public List<TodoDto> getAllTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<TodoDto> getActiveTodos() {
        return repository.findByCompletedFalseOrderByPriorityDescCompletedDesc()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<TodoDto> getCompletedTodos() {
        return repository.findByCompletedTrueAndOrderByUpdatedAtDesc()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public TodoDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));
    }

    public TodoDto createTodo(CreateTodoDto todoDto) {
        var todo = mapper.toEntity(todoDto);
        var savedTodo = repository.save(todo);
        return mapper.toDto(savedTodo);
    }

    public TodoDto updateTodo(Long id, UpdateTodoDto todoDto) {
        var existedTodo = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Todo not found with id: " + id));
        mapper.updateEntity(existedTodo, todoDto);
        var updatedTodo = repository.save(existedTodo);
        return mapper.toDto(updatedTodo);
    }

    public void deleteTodo(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Todo not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public TodoDto markAsCompleted(Long id) {
        var todo = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));
        todo.setCompleted(true);
        todo.setUpdatedAt(LocalDateTime.now());
        var savedTodo = repository.save(todo);
        return mapper.toDto(savedTodo);
    }

    public List<TodoDto> searchTodos(String searchItem) {
        if (searchItem == null || searchItem.trim().isEmpty()) {
            return getAllTodos();
        }
        return repository.findByTitleOrDescriptionContainingIgnoreCase(searchItem.trim())
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<TodoDto> getOverdueTodos() {
        return repository.findByOverdueTodos(LocalDateTime.now())
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<TodoDto> getTodayTodos() {
        return repository.findTodoForDate(LocalDateTime.now())
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public TodoStats getStats() {
        long totalTodos = repository.count();
        long completedTodos = repository.countByCompletedTrue();
        long activeTodos = repository.countByCompletedFalse();
        long overdueTodos = getOverdueTodos().size();

        return TodoStats.builder()
                .totalTodos(totalTodos)
                .completedTodos(completedTodos)
                .activeTodos(activeTodos)
                .overdueTodos(overdueTodos)
                .build();
    }
}
