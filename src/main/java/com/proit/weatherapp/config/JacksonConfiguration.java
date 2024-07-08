package com.proit.weatherapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;


@Configuration
public class JacksonConfiguration {

    /**
     * Support for Java date and time API.
     *
     * @return the corresponding Jackson module.
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    Jackson2ObjectMapperBuilder getBuilder() {
        return jackson2ObjectMapperBuilder();
    }

    static Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        JavaTimeModule module = new JavaTimeModule();

        return new Jackson2ObjectMapperBuilder()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .findModulesViaServiceLoader(true)
                .modulesToInstall(module)
                .failOnEmptyBeans(false)
                .failOnUnknownProperties(false);
    }

    @Bean
    public static ObjectMapper buildObjectMapper() {
        return jackson2ObjectMapperBuilder().build();
    }
}
