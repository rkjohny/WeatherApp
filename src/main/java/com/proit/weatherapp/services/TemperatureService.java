package com.proit.weatherapp.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.proit.weatherapp.dto.TemperatureDaily;
import com.proit.weatherapp.dto.TemperatureDailyResponse;
import com.proit.weatherapp.security.Authority;
import com.proit.weatherapp.core.OpenMetroService;
import com.proit.weatherapp.core.JsonService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RolesAllowed(value = Authority.USER)
public class TemperatureService {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureService.class);

    private final OpenMetroService openMetroService;
    private final JsonService jsonService;

    public TemperatureService(OpenMetroService openMetroService, JsonService jsonService) {
        this.openMetroService = openMetroService;
        this.jsonService = jsonService;
    }

    @NotNull
    public List<TemperatureDaily> getDailyTemperature(Double latitude, Double longitude, String timeZone) {
        List<TemperatureDaily> dailyTemperatureList = new ArrayList<>();

        if (latitude != null && longitude != null & !StringUtils.isBlank(timeZone)) {
            String response = openMetroService.getTemperatureDaily(latitude, longitude, timeZone);
            JsonNode daily = jsonService.getProperty(response, "daily");
            if (daily != null) {
                TemperatureDailyResponse dailyResponse = (TemperatureDailyResponse) jsonService.fromJson(daily, TemperatureDailyResponse.class);
                if (dailyResponse != null) {
                    dailyTemperatureList.addAll(dailyResponse.getDailyList());
                }
            }
        }
        return dailyTemperatureList;
    }

}
