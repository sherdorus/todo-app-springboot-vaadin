package io.sherdor.todoapp.config;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = "todo", variant = Lumo.DARK)
public class AppShellConfig implements AppShellConfigurator {
}
