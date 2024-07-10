package com.proit.weatherapp.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.proit.weatherapp.dto.*;
import com.proit.weatherapp.security.Authority;
import com.proit.weatherapp.core.OpenMetroService;
import com.proit.weatherapp.core.JsonService;
import com.proit.weatherapp.types.OpenMetroAPIParam;
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
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final OpenMetroService openMetroService;
    private final JsonService jsonService;

    public WeatherService(OpenMetroService openMetroService, JsonService jsonService) {
        this.openMetroService = openMetroService;
        this.jsonService = jsonService;
    }

    @NotNull
    public List<WeatherDataDaily> getDailyWeather(Double latitude, Double longitude, String timeZone) {
        List<WeatherDataDaily> dailyTemperatureList = new ArrayList<>();

        if (latitude != null && longitude != null & !StringUtils.isBlank(timeZone)) {
            String response = openMetroService.getWeatherDaily(latitude, longitude, timeZone);
            JsonNode daily = jsonService.getProperty(response, OpenMetroAPIParam.DAILY);
            if (daily != null) {
                WeatherResponseDaily dailyResponse = (WeatherResponseDaily) jsonService.fromJson(daily, WeatherResponseDaily.class);
                if (dailyResponse != null) {
                    dailyTemperatureList.addAll(dailyResponse.getDailyList());
                }
            }
        }
        return dailyTemperatureList;
    }

    @NotNull
    public List<WeatherDataHourly> getHourlyWeather(Double latitude, Double longitude, String timeZone, String date) {
        List<WeatherDataHourly> hourlyTemperatureList = new ArrayList<>();

        if (latitude != null && longitude != null & !StringUtils.isBlank(timeZone)) {
            String response = openMetroService.getWeatherHourly(latitude, longitude, timeZone, date);
            JsonNode hourly = jsonService.getProperty(response, OpenMetroAPIParam.HOURLY);
            if (hourly != null) {
                WeatherResponseHourly hourlyResponse = (WeatherResponseHourly) jsonService.fromJson(hourly, WeatherResponseHourly.class);
                if (hourlyResponse != null) {
                    hourlyTemperatureList.addAll(hourlyResponse.getHourlyList());
                }
            }
        }
        return hourlyTemperatureList;
    }

    @NotNull
    public WeatherResponseCurrent getCurrentWeather(Double latitude, Double longitude, String timeZone) {
        WeatherResponseCurrent responseCurrent = new WeatherResponseCurrent();

        if (latitude != null && longitude != null & !StringUtils.isBlank(timeZone)) {
            String response = openMetroService.getWeatherCurrent(latitude, longitude, timeZone);
            JsonNode current = jsonService.getProperty(response, OpenMetroAPIParam.CURRENT);
            if (current != null) {
                return jsonService.fromJson(current, WeatherResponseCurrent.class);

            }
        }
        return responseCurrent;
    }

    @NotNull
    public List<WeatherDataCurrent> getCurrentWeather(List<Location> locations) {
        List<WeatherDataCurrent> weatherDataCurrents = new ArrayList<>();
        locations.forEach(location -> {
            WeatherResponseCurrent response = getCurrentWeather(location.getLatitude(), location.getLongitude(), location.getTimezone());
            weatherDataCurrents.add(response.toCurrentData(location));
        });
        return weatherDataCurrents;
    }
}
