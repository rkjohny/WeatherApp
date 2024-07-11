package com.proit.weatherapp;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;


/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@Theme(value = "weatherapp")
@PWA(
        name = "WeatherApp",
        shortName = "WeatherApp"
)
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
        // Set default locale
        Locale.setDefault(Locale.ENGLISH);
    }
}
