package io.sherdor.todoapp.mappers;

import io.sherdor.todoapp.dto.CreateTodoDto;
import io.sherdor.todoapp.dto.TodoDto;
import io.sherdor.todoapp.dto.UpdateTodoDto;
import io.sherdor.todoapp.entity.Todo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TodoMapper {
    TodoDto toDto(Todo todo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Todo toEntity(TodoDto todoDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(TodoDto todoDto, @MappingTarget Todo todo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completed", constant = "false")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", ignore = true)
    Todo toEntity(CreateTodoDto createTodoDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void updateEntity(@MappingTarget Todo todo, UpdateTodoDto updateTodoDto);}