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

    /**
     * Get daily weather forecast (of 16 days) of a location specified by latitude, longitude and the timezone
     *
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @param timezone timezone of the location
     * @return the {@link List<WeatherDataDaily>} the list of the daily forecast data
     */
    @NotNull
    public List<WeatherDataDaily> getDailyWeather(Double latitude, Double longitude, String timezone) {
        List<WeatherDataDaily> dailyTemperatureList = new ArrayList<>();

        if (latitude != null && longitude != null & !StringUtils.isBlank(timezone)) {
            String response = openMetroService.getWeatherDaily(latitude, longitude, timezone);
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

    /**
     * Get hourly weather forecast (of 24 hours) of a specific day of a location specified by latitude, longitude and the timezone
     *
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @param timezone timezone of the location
     * @param date the specified day in the format 'yyyy-mm-dd'
     * @return the {@link List<WeatherDataHourly>} the list of the hourly forecast data (for 24 hours)
     */
    @NotNull
    public List<WeatherDataHourly> getHourlyWeather(Double latitude, Double longitude, String timezone, String date) {
        List<WeatherDataHourly> hourlyTemperatureList = new ArrayList<>();

        if (latitude != null && longitude != null & !StringUtils.isBlank(timezone)) {
            String response = openMetroService.getWeatherHourly(latitude, longitude, timezone, date);
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

    /**
     * Get the current weather information of a location specified by latitude, longitude and the timezone
     *
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @param timezone timezone of the location
     * @return the {@link WeatherResponseCurrent} the current weather data
     */
    @NotNull
    private WeatherResponseCurrent getCurrentWeather(Double latitude, Double longitude, String timezone) {
        WeatherResponseCurrent responseCurrent = new WeatherResponseCurrent();

        if (latitude != null && longitude != null & !StringUtils.isBlank(timezone)) {
            String response = openMetroService.getWeatherCurrent(latitude, longitude, timezone);
            JsonNode current = jsonService.getProperty(response, OpenMetroAPIParam.CURRENT);
            if (current != null) {
                return jsonService.fromJson(current, WeatherResponseCurrent.class);

            }
        }
        return responseCurrent;
    }

    /**
     * Get the current weather information of a list of locations specified by latitude, longitude and the timezone
     *
     * @param locations list of locations
     * @return the {@link List<WeatherDataCurrent>} the list of current weather data for the specified location list
     */
    @NotNull
    public List<WeatherDataCurrent> getCurrentWeather(@NotNull List<Location> locations) {
        List<WeatherDataCurrent> weatherDataCurrents = new ArrayList<>();
        locations.forEach(location -> {
            WeatherResponseCurrent response = getCurrentWeather(location.getLatitude(), location.getLongitude(), location.getTimezone());
            weatherDataCurrents.add(response.toCurrentData(location));
        });
        return weatherDataCurrents;
    }
}
