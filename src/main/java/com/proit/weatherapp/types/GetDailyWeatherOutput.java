package com.proit.weatherapp.types;

import com.proit.weatherapp.dto.WeatherDataDaily;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetDailyWeatherOutput extends AbstractOutput {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<WeatherDataDaily> dataDailies = new ArrayList<>();
}
