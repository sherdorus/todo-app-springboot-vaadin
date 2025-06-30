package io.sherdor.todoapp.controller;


import io.sherdor.todoapp.dto.TodoDto;
import io.sherdor.todoapp.mappers.TodoMapper;
import io.sherdor.todoapp.repositories.TodoRepository;
import io.sherdor.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
