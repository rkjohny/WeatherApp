package com.proit.weatherapp.core;

import com.proit.weatherapp.services.WeatherService;
import com.proit.weatherapp.types.GetHourlyWeatherInput;
import com.proit.weatherapp.types.GetHourlyWeatherOutput;
import com.proit.weatherapp.util.InputValidationUtils;
import com.proit.weatherapp.util.Sanitizer;
import com.proit.weatherapp.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class GetHourlyWeatherHelper extends AbstractHelper<GetHourlyWeatherInput, GetHourlyWeatherOutput> {
    private final WeatherService weatherService;

    @Override
    protected void validateInput(GetHourlyWeatherInput input, Object... args) {
        InputValidationUtils.validateObject(input.getLatitude());
        InputValidationUtils.validateObject(input.getLongitude());
        InputValidationUtils.validateString(input.getTimezone());
        if (StringUtils.isBlank(input.getDate())) {
            input.setDate(Utils.getCurrentDate(input.getTimezone()));
        } else {
            input.setDate(Sanitizer.sanitize(input.getDate().trim()));
        }
        input.setTimezone(Sanitizer.sanitize(input.getTimezone().trim()));
    }

    @Override
    protected void checkPermission(GetHourlyWeatherInput input, Object... args) {

    }

    @Override
    protected GetHourlyWeatherOutput executeHelper(GetHourlyWeatherInput input, Object... args) {
        GetHourlyWeatherOutput output = new GetHourlyWeatherOutput();
        output.setDataHourlies(weatherService.getHourlyWeather(input.getLatitude(), input.getLongitude(), input.getTimezone(), input.getDate()));
        return output;
    }
}
