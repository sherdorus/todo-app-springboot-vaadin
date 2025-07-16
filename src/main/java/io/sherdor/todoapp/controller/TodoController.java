package io.sherdor.todoapp.controller;


import io.sherdor.todoapp.config.TodoStats;
import io.sherdor.todoapp.dto.CreateTodoDto;
import io.sherdor.todoapp.dto.TodoDto;
import io.sherdor.todoapp.dto.UpdateTodoDto;
import io.sherdor.todoapp.mappers.TodoMapper;
import io.sherdor.todoapp.repositories.TodoRepository;
import io.sherdor.todoapp.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository repository;
    private final TodoService service;
    private final TodoMapper mapper;

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = service.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/active")
    public ResponseEntity<List<TodoDto>> getActiveTodos() {
        List<TodoDto> todos = service.getActiveTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TodoDto>> getCompletedTodos() {
        List<TodoDto> todos = service.getCompletedTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id) {
        var todo = service.getById(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@Valid @RequestBody CreateTodoDto todo) {
        var createdTodo = service.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id, @Valid @RequestBody UpdateTodoDto todoDto) {
        var updatedTodo = service.updateTodo(id, todoDto);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        service.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id) {
        var completed = service.toggleCompleted(id, true);
        return ResponseEntity.ok().body(completed);
    }

    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TodoDto> incompleteTodo(@PathVariable Long id) {
        var completed = service.toggleCompleted(id, false);
        return ResponseEntity.ok().body(completed);
    }

    @GetMapping("search")
    public ResponseEntity<List<TodoDto>> searchTodo(@RequestParam String search) {
        List<TodoDto> todos = service.searchTodos(search);
        return ResponseEntity.ok().body(todos);
    }

    @GetMapping("overdue")
    public ResponseEntity<List<TodoDto>> getOverdueTodos() {
        var overdue = service.getOverdueTodos();
        return ResponseEntity.ok().body(overdue);
    }

    @GetMapping("/today")
    public ResponseEntity<List<TodoDto>> getTodayTodos() {
        var today = service.getTodayTodos();
        return ResponseEntity.ok().body(today);

    }

    @GetMapping("/stats")
    public ResponseEntity<TodoStats> getStats() {
        var stats = service.getStats();
        return ResponseEntity.ok().body(stats);
    }
}