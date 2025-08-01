package io.sherdor.todoapp.view;

import io.sherdor.todoapp.config.TodoStats;
import io.sherdor.todoapp.dto.CreateTodoDto;
import io.sherdor.todoapp.dto.TodoDto;
import io.sherdor.todoapp.dto.UpdateTodoDto;
import io.sherdor.todoapp.enums.Priority;
import io.sherdor.todoapp.service.TodoService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Route("")
@PageTitle("Todo App")
@SpringComponent
@UIScope
public class TodoMainView extends VerticalLayout {

    private final TodoService todoService;
    private final Grid<TodoDto> grid;
    private final TextField searchField;
    private final Button addButton;
    private final Span statsSpan;
    private final HorizontalLayout statsLayout;
    private Binder<TodoFormData> binder;
    private TodoDto editingTodo;

    // Helper class for form binding since TodoDto is immutable
    public static class TodoFormData {
        private String title = "";
        private String description = "";
        private Priority priority = Priority.MEDIUM;
        private LocalDateTime dueDate;
        private boolean completed = false;

        // Getters and setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Priority getPriority() { return priority; }
        public void setPriority(Priority priority) { this.priority = priority; }

        public LocalDateTime getDueDate() { return dueDate; }
        public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

        public boolean isCompleted() { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }
    }

    @Autowired
    public TodoMainView(TodoService todoService) {
        this.todoService = todoService;
        this.statsLayout = new HorizontalLayout();
        this.grid = new Grid<>(TodoDto.class, false);
        this.searchField = new TextField();
        this.addButton = new Button("Add Task", new Icon(VaadinIcon.PLUS));
        this.statsSpan = new Span();

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        setupComponents();
        setupLayout();
        refreshGrid();
        updateStats();
    }

    private void setupComponents() {
        searchField.setPlaceholder("Search tasks...");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.addValueChangeListener(e -> {
            String searchTerm = e.getValue();
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                refreshGrid();
            } else {
                grid.setItems(todoService.searchTodos(searchTerm));
            }
        });

        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClickListener(e -> openTodoDialog(null));

        setupGrid();
    }

    private void setupGrid() {
        grid.addColumn(todo -> todo.isCompleted() ? "✓" : "✗")
                .setHeader("Status")
                .setWidth("80px")
                .setFlexGrow(0);

        grid.addColumn(TodoDto::getTitle)
                .setHeader("Title")
                .setFlexGrow(1);

        grid.addColumn(todo -> todo.getPriority().getDisplayName())
                .setHeader("Priority")
                .setWidth("120px");

        grid.addComponentColumn(todo -> {
            if (todo.getDueDate() == null) return new Span("");

            Span dueDateSpan = new Span(todo.getDueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

            if (!todo.isCompleted() && todo.getDueDate().isBefore(LocalDateTime.now())) {
                dueDateSpan.getStyle().set("color", "var(--lumo-error-color)");
                dueDateSpan.getStyle().set("font-weight", "bold");
            }

            return dueDateSpan;
        }).setHeader("Due Date").setWidth("150px");

        grid.addColumn(todo -> todo.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .setHeader("Created")
                .setWidth("150px");

        grid.addComponentColumn(this::createActionButtons)
                .setHeader("Actions")
                .setWidth("200px")
                .setFlexGrow(0);

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.addItemDoubleClickListener(e -> openTodoDialog(e.getItem()));

        grid.setClassNameGenerator(todo -> {
            if (todo.isCompleted()) {
                return "completed-todo";
            } else if (todo.getDueDate() != null && todo.getDueDate().isBefore(LocalDateTime.now())) {
                return "overdue-todo";
            }
            return "";
        });
    }

    private Component createActionButtons(TodoDto todo) {
        HorizontalLayout actions = new HorizontalLayout();
        actions.setSpacing(true);

        Button statusButton = new Button();
        if (todo.isCompleted()) {
            statusButton.setIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
            statusButton.setText("Undo");
            statusButton.addClickListener(e -> {
                todoService.toggleCompleted(todo.getId(), false);
                refreshGrid();
                updateStats();
                showSuccessNotification("Task returned to active");
            });
        } else {
            statusButton.setIcon(new Icon(VaadinIcon.CHECK));
            statusButton.setText("Complete");
            statusButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            statusButton.addClickListener(e -> {
                todoService.toggleCompleted(todo.getId(), true);
                refreshGrid();
                updateStats();
                showSuccessNotification("Task completed!");
            });
        }

        Button editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        editButton.addClickListener(e -> openTodoDialog(todo));

        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.addClickListener(e -> confirmDelete(todo));

        actions.add(statusButton, editButton, deleteButton);
        return actions;
    }

    private void setupLayout() {
        H1 title = new H1("📝 Todo App");
        title.getStyle().set("margin", "0");

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout leftSection = new HorizontalLayout(searchField);
        HorizontalLayout rightSection = new HorizontalLayout(addButton);

        toolbar.add(leftSection, rightSection);

        updateStats();
        add(title, statsLayout, toolbar, grid);

        getElement().getStyle().set("--lumo-font-family", "Arial, sans-serif");
    }

    private void openTodoDialog(TodoDto todo) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(todo == null ? "New Task" : "Edit Task");
        dialog.setWidth("500px");
        dialog.setMaxWidth("90vw");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setPadding(false);
        formLayout.setSpacing(true);

        TextField titleField = new TextField("Title");
        titleField.setWidthFull();
        titleField.setRequired(true);
        titleField.setMaxLength(255);

        TextArea descriptionField = new TextArea("Description");
        descriptionField.setWidthFull();
        descriptionField.setMaxLength(1000);

        ComboBox<Priority> priorityField = new ComboBox<>("Priority");
        priorityField.setItems(Priority.values());
        priorityField.setItemLabelGenerator(Priority::getDisplayName);
        priorityField.setWidthFull();

        DateTimePicker dueDateField = new DateTimePicker("Due Date");
        dueDateField.setWidthFull();

        Checkbox completedField = new Checkbox("Completed");

        formLayout.add(titleField, descriptionField, priorityField, dueDateField, completedField);

        // Create form data object for binding
        TodoFormData formData = new TodoFormData();

        // Initialize form data from existing todo if editing
        if (todo != null) {
            formData.setTitle(todo.getTitle());
            formData.setDescription(todo.getDescription());
            formData.setPriority(todo.getPriority());
            formData.setDueDate(todo.getDueDate());
            formData.setCompleted(todo.isCompleted());
        }

        binder = new Binder<>(TodoFormData.class);

        binder.forField(titleField)
                .asRequired("Title is required")
                .withValidator(title -> title.length() <= 255, "Title cannot be longer than 255 characters")
                .bind(TodoFormData::getTitle, TodoFormData::setTitle);

        binder.forField(descriptionField)
                .withValidator(desc -> desc == null || desc.length() <= 1000, "Description cannot be longer than 1000 characters")
                .bind(TodoFormData::getDescription, TodoFormData::setDescription);

        binder.forField(priorityField)
                .asRequired("Priority is required")
                .bind(TodoFormData::getPriority, TodoFormData::setPriority);

        binder.bind(dueDateField, TodoFormData::getDueDate, TodoFormData::setDueDate);
        binder.bind(completedField, TodoFormData::isCompleted, TodoFormData::setCompleted);

        binder.setBean(formData);

        editingTodo = todo;

        Button saveButton = new Button("Save", new Icon(VaadinIcon.CHECK));
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickShortcut(Key.ENTER);

        Button cancelButton = new Button("Cancel", new Icon(VaadinIcon.CLOSE));

        saveButton.addClickListener(e -> {
            try {
                binder.writeBean(formData);

                if (editingTodo == null) {
                    // Create new todo
                    CreateTodoDto createDto = new CreateTodoDto(
                            formData.getTitle(),
                            formData.getDescription(),
                            formData.getPriority().ordinal(), // Convert Priority enum to Integer
                            formData.getDueDate()
                    );
                    todoService.createTodo(createDto);
                    showSuccessNotification("Task created successfully!");
                } else {
                    // Update existing todo
                    UpdateTodoDto updateDto = new UpdateTodoDto(
                            formData.getTitle(),
                            formData.getDescription(),
                            formData.getPriority().ordinal(), // Convert Priority enum to Integer
                            formData.isCompleted(), // Boolean completed
                            formData.getDueDate()
                    );
                    todoService.updateTodo(editingTodo.getId(), updateDto);
                    showSuccessNotification("Task updated successfully!");
                }

                refreshGrid();
                updateStats();
                dialog.close();

            } catch (ValidationException ex) {
                showErrorNotification("Please fix the validation errors");
            } catch (Exception ex) {
                showErrorNotification("An error occurred while saving: " + ex.getMessage());
            }
        });

        cancelButton.addClickListener(e -> dialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        dialog.add(formLayout);
        dialog.getFooter().add(buttonLayout);

        dialog.open();
        titleField.focus();
    }

    private void confirmDelete(TodoDto todo) {
        Dialog confirmDialog = new Dialog();
        confirmDialog.setHeaderTitle("Confirm Deletion");

        VerticalLayout content = new VerticalLayout();
        content.add(new Span("Are you sure you want to delete the task \"" + todo.getTitle() + "\"?"));
        content.add(new Span("This action cannot be undone."));

        Button deleteButton = new Button("Delete", new Icon(VaadinIcon.TRASH));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(e -> {
            try {
                todoService.deleteTodo(todo.getId());
                refreshGrid();
                updateStats();
                showSuccessNotification("Task deleted successfully");
                confirmDialog.close();
            } catch (Exception ex) {
                showErrorNotification("Failed to delete task: " + ex.getMessage());
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(e -> confirmDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(deleteButton, cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        confirmDialog.add(content);
        confirmDialog.getFooter().add(buttonLayout);
        confirmDialog.open();

        deleteButton.focus();
    }

    private void refreshGrid() {
        try {
            List<TodoDto> todos = todoService.getAllTodos();
            grid.setItems(todos);
        } catch (Exception ex) {
            showErrorNotification("Failed to load tasks: " + ex.getMessage());
        }
    }

    private void updateStats() {
        statsLayout.removeAll();

        try {
            TodoStats stats = todoService.getStats();

            Span total = createStatSpan("📊 Total: " + stats.getTotalTodos(),
                    () -> grid.setItems(todoService.getAllTodos()));
            Span completed = createStatSpan("✅ Completed: " + stats.getCompletedTodos(),
                    () -> grid.setItems(todoService.getCompletedTodos()));
            Span active = createStatSpan("⏳ Active: " + stats.getActiveTodos(),
                    () -> grid.setItems(todoService.getActiveTodos()));
            Span overdue = createStatSpan("⚠️ Overdue: " + stats.getOverdueTodos(),
                    () -> grid.setItems(todoService.getOverdueTodos()));

            statsLayout.add(total, completed, active, overdue);
            statsLayout.setSpacing(true);
            statsLayout.getStyle()
                    .set("font-size", "14px")
                    .set("color", "var(--lumo-secondary-text-color)")
                    .set("flex-wrap", "wrap");
        } catch (Exception ex) {
            showErrorNotification("Failed to load statistics: " + ex.getMessage());
        }
    }

    private Span createStatSpan(String text, Runnable onClick) {
        Span span = new Span(text);
        span.getStyle()
                .set("cursor", "pointer")
                .set("margin-right", "15px")
                .set("user-select", "none")
                .set("padding", "5px 10px")
                .set("border-radius", "4px")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("transition", "background-color 0.2s");

        span.getElement().addEventListener("mouseenter", e -> {
            span.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
        });

        span.getElement().addEventListener("mouseleave", e -> {
            span.getStyle().set("background-color", "var(--lumo-contrast-5pct)");
        });

        span.addClickListener(e -> {
            try {
                onClick.run();
            } catch (Exception ex) {
                showErrorNotification("Failed to filter tasks: " + ex.getMessage());
            }
        });

        return span;
    }

    private void showSuccessNotification(String message) {
        Notification notification = Notification.show(message, 3000, Notification.Position.TOP_END);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void showErrorNotification(String message) {
        Notification notification = Notification.show(message, 5000, Notification.Position.TOP_END);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}