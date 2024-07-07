package com.proit.weatherapp.interceptor;

import com.proit.weatherapp.services.DataGeneratingService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final DataGeneratingService dataGeneratingService;

    public AppEventListener(DataGeneratingService dataGeneratingService) {
        this.dataGeneratingService = dataGeneratingService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            dataGeneratingService.generateUser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
