package com.proit.weatherapp.core;

import com.proit.weatherapp.services.WeatherService;
import com.proit.weatherapp.types.GetDailyWeatherInput;
import com.proit.weatherapp.types.GetDailyWeatherOutput;
import com.proit.weatherapp.util.InputValidationUtils;
import com.proit.weatherapp.util.Sanitizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class GetDailyWeatherHelper extends AbstractHelper<GetDailyWeatherInput, GetDailyWeatherOutput> {
    private final WeatherService weatherService;

    @Override
    protected void validateInput(GetDailyWeatherInput input, Object... args) {
        InputValidationUtils.validateObject(input.getLatitude());
        InputValidationUtils.validateObject(input.getLongitude());
        InputValidationUtils.validateString(input.getTimezone());

        input.setTimezone(Sanitizer.sanitize(input.getTimezone().trim()));
    }

    @Override
    protected void checkPermission(GetDailyWeatherInput input, Object... args) {

    }

    @Override
    protected GetDailyWeatherOutput executeHelper(GetDailyWeatherInput input, Object... args) {
        GetDailyWeatherOutput output = new GetDailyWeatherOutput();
        output.setDataDailies(weatherService.getDailyWeather(input.getLatitude(), input.getLongitude(), input.getTimezone()));
        return output;
    }
}
