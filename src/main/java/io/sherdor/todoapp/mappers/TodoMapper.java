package io.sherdor.todoapp.mappers;

import io.sherdor.todoapp.dto.TodoDto;
import io.sherdor.todoapp.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
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
}
