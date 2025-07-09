package io.sherdor.todoapp.config;

import io.sherdor.todoapp.entity.Todo;
import io.sherdor.todoapp.enums.Priority;
import io.sherdor.todoapp.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final TodoRepository todoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (todoRepository.count() == 0) {
            initializeTestData();
        }
    }

    private void initializeTestData() {
        List<Todo> sampleTodos = Arrays.asList(
                Todo.builder()
                        .title("Buy groceries")
                        .description("Milk, bread, eggs, vegetables for salad")
                        .priority(Priority.HIGH)
                        .dueDate(LocalDateTime.now().plusHours(3))
                        .completed(false)
                        .createdAt(LocalDateTime.now().minusHours(2))
                        .build(),

                Todo.builder()
                        .title("Write report")
                        .description("Prepare monthly project report")
                        .priority(Priority.URGENT)
                        .dueDate(LocalDateTime.now().plusDays(1))
                        .completed(false)
                        .createdAt(LocalDateTime.now().minusDays(1))
                        .build(),

                Todo.builder()
                        .title("Call doctor")
                        .description("Schedule an appointment with the therapist")
                        .priority(Priority.MEDIUM)
                        .dueDate(LocalDateTime.now().plusDays(2))
                        .completed(false)
                        .createdAt(LocalDateTime.now().minusHours(5))
                        .build(),

                Todo.builder()
                        .title("Study Spring Boot")
                        .description("Complete an online Spring Boot course and create a pet project")
                        .priority(Priority.LOW)
                        .dueDate(LocalDateTime.now().plusWeeks(1))
                        .completed(true)
                        .createdAt(LocalDateTime.now().minusDays(7))
                        .updatedAt(LocalDateTime.now().minusDays(1))
                        .build(),

                Todo.builder()
                        .title("Clean apartment")
                        .description("General cleaning: vacuum, mop floors, dust")
                        .priority(Priority.MEDIUM)
                        .dueDate(LocalDateTime.now().plusDays(3))
                        .completed(false)
                        .createdAt(LocalDateTime.now().minusHours(8))
                        .build(),

                Todo.builder()
                        .title("Overdue task")
                        .description("This is an example of an overdue task for demonstration")
                        .priority(Priority.HIGH)
                        .dueDate(LocalDateTime.now().minusDays(1))
                        .completed(false)
                        .createdAt(LocalDateTime.now().minusDays(3))
                        .build(),

                Todo.builder()
                        .title("Prepare presentation")
                        .description("Create a presentation for a client meeting")
                        .priority(Priority.URGENT)
                        .dueDate(LocalDateTime.now().plusHours(6))
                        .completed(false)
                        .createdAt(LocalDateTime.now().minusHours(12))
                        .build(),

                Todo.builder()
                        .title("Order gift")
                        .description("Choose and order a birthday gift")
                        .priority(Priority.LOW)
                        .completed(true)
                        .createdAt(LocalDateTime.now().minusDays(5))
                        .updatedAt(LocalDateTime.now().minusDays(2))
                        .build()
        );
        todoRepository.saveAll(sampleTodos);
    }
}
