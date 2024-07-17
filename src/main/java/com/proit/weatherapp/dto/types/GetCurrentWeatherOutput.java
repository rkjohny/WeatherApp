package com.proit.weatherapp.dto.types;

import com.proit.weatherapp.dto.WeatherDataCurrent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetCurrentWeatherOutput extends AbstractOutput {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<WeatherDataCurrent> dataCurrents = new ArrayList<>();
}
