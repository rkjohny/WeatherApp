package com.proit.weatherapp.api;

import com.proit.weatherapp.core.GetCurrentWeatherHelper;
import com.proit.weatherapp.core.GetDailyWeatherHelper;
import com.proit.weatherapp.core.GetHourlyWeatherHelper;
import com.proit.weatherapp.dto.*;
import com.proit.weatherapp.dto.types.*;
import com.proit.weatherapp.services.WeatherService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@PermitAll
public class WeatherApi {

    private final ObjectProvider<GetDailyWeatherHelper> getDailyWeatherHelperProvider;
    private final ObjectProvider<GetHourlyWeatherHelper> getHourlyWeatherHelperProvider;
    private final ObjectProvider<GetCurrentWeatherHelper> getCurrentWeatherHelperProvider;
    private final WeatherService weatherService;

    /**
     * Get daily weather forecast (of 16 days) of a location specified by latitude, longitude and the timezone
     *
     * @param input the {@link GetDailyWeatherInput} object that contains the latitude, longitude and timezone
     *              of a location whose daily weather forecast will be returned.
     * @return {@link GetDailyWeatherOutput} object which contains list of {@link WeatherDataDaily} object that
     * contains the daily forecast data
     */
    public GetDailyWeatherOutput getDailyWeather(GetDailyWeatherInput input) {
        GetDailyWeatherHelper helper = getDailyWeatherHelperProvider.getObject();
        return helper.execute(input);
    }

    /**
     * Get hourly weather forecast (of 24 hours) of a specific day of a location specified by latitude, longitude and the timezone
     *
     * @param input the {@link GetHourlyWeatherInput} object that contains the latitude, longitude, timezone
     *              of a location whose hourly (for 24 hours) weather forecast will be returned of the date specified.
     *              If date is null api will return the hourly forecast of current day
     * @return {@link GetHourlyWeatherOutput} object which contains list of {@link WeatherDataHourly} object that
     * contains the hourly forecast data
     */
    public GetHourlyWeatherOutput getHourlyWeather(GetHourlyWeatherInput input) {
        GetHourlyWeatherHelper helper = getHourlyWeatherHelperProvider.getObject();
        return helper.execute(input);
    }

    /**
     * Get the current weather information of a list of locations
     *
     * @param input the {@link GetCurrentWeatherInput} object that contains the list of location whose current weather forecast will be returned
     * @return {@link GetCurrentWeatherOutput} object which contains list of {@link WeatherDataCurrent} object that
     * contains the current weather forecast data
     */
    public GetCurrentWeatherOutput getCurrentWeather(GetCurrentWeatherInput input) {
        GetCurrentWeatherHelper helper = getCurrentWeatherHelperProvider.getObject();
        return helper.execute(input);
    }
}
