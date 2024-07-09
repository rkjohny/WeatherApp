package com.proit.weatherapp.interceptor;

import com.proit.weatherapp.core.DataInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final DataInitializer dataInitializer;

    public AppEventListener(DataInitializer dataInitializer) {
        this.dataInitializer = dataInitializer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            dataInitializer.generateUser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
