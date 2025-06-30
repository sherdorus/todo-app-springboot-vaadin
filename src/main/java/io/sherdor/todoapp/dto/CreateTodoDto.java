package io.sherdor.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CreateTodoDto {
    @Size(message = "Title cannot be longer than 255 characters", max = 255)
    @NotBlank(message = "Title cannot be empty")
    String title;

    @Size(message = "Description cannot be longer than 1000 characters", max = 1000)
    String description;

    @NotNull(message = "Priority is required")
    Integer priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime dueDate;
}
