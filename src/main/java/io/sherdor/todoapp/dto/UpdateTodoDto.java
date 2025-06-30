package io.sherdor.todoapp.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UpdateTodoDto {

    String title;
    String description;
    Integer priority;
    Boolean completed;
    LocalDateTime dueDate;
}
