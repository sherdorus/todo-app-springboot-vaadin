package io.sherdor.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.sherdor.todoapp.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import java.time.LocalDateTime;

@Value
public class TodoDto{

    Long id;

    @Size(message = "Title cannot be longer than 255 characters", max = 255)
    @NotBlank(message = "Title cannot be empty")
    String title;

    @Size(message = "Description cannot be longer than 1000 characters", max = 1000)
    String description;

    boolean completed;

    Priority priority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime updatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime dueDate;
}