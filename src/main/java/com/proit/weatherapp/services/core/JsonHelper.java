package com.proit.weatherapp.services.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class JsonHelper {
    private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);

    private ObjectMapper objectMapper;

    public JsonHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private void logError(Exception e) {
        log.warn("JSON error :" + e.getMessage());
    }

    public String createJson(Map.Entry<String, Object>... entries) {
        Map<String, Object> m = new HashMap<>(entries.length);
        for (Map.Entry<String, Object> entry : entries) {
            m.put(entry.getKey(), entry.getValue());
        }
        return toJson(m);
    }

    @NotNull
    public String toJson(Object o) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            logError(e);
        }
        return json;
    }

    @Nullable
    public Object fromJson(String jsonString, Class clazz) {
        Object o = null;
        try {
            o = objectMapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            logError(e);
        }
        return o;
    }

    @Nullable
    public Object fromJson(JsonNode jsonNode, Class clazz) {
        Object o = null;
        try {
            o = fromJson(jsonNode.toString(), clazz);
        } catch (Exception e) {
            logError(e);
        }
        return o;
    }

    @NotNull
    public String toPrettyJson(Object o) {
        String json = "";
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (Exception e) {
            logError(e);
        }
        return json;
    }

    @Nullable
    public JsonNode getProperty(String jsonString, String propertyName) {
        try {
            // Parse JSON string to JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            return jsonNode.get(propertyName);
        } catch (Exception e) {
            logError(e);
        }
        return null;
    }

    @NotNull
    public <T> List<T> fromJsonToList(JsonNode jsonNode, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            if (jsonNode.isArray()) {
                var iterator = jsonNode.elements();
                while (iterator.hasNext()) {
                    T object = (T) fromJson(iterator.next(), clazz);
                    if (object != null) {
                        list.add(object);
                    }
                }
            }
        } catch (Exception e) {
            logError(e);
        }
        return list;
    }
}
