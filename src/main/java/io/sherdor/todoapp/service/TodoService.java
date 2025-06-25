package io.sherdor.todoapp.service;

import io.sherdor.todoapp.entity.Todo;
import io.sherdor.todoapp.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository repository;

    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

}
