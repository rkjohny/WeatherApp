package com.proit.weatherapp.types;

import com.proit.weatherapp.dto.WeatherDataHourly;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetHourlyWeatherOutput extends AbstractOutput {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<WeatherDataHourly> dataHourlies = new ArrayList<>();
}
