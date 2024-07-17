package com.proit.weatherapp.core;

import com.proit.weatherapp.services.WeatherService;
import com.proit.weatherapp.dto.types.GetCurrentWeatherInput;
import com.proit.weatherapp.dto.types.GetCurrentWeatherOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class GetCurrentWeatherHelper extends AbstractHelper<GetCurrentWeatherInput, GetCurrentWeatherOutput> {
    private final WeatherService weatherService;


    @Override
    protected void validateInput(GetCurrentWeatherInput input, Object... args) {
        if (input.getLocations() == null) {
            input.setLocations(new ArrayList<>());
        }
    }

    @Override
    protected void checkPermission(GetCurrentWeatherInput input, Object... args) {

    }

    @Override
    protected GetCurrentWeatherOutput executeHelper(GetCurrentWeatherInput input, Object... args) {
        GetCurrentWeatherOutput output = new GetCurrentWeatherOutput();
        output.setDataCurrents(weatherService.getCurrentWeather(input.getLocations()));
        return output;
    }
}
