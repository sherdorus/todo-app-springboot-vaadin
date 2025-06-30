package io.sherdor.todoapp.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoStats {
    private long totalTodos;
    private long completedTodos;
    private long activeTodos;
    private long overdueTodos;}
