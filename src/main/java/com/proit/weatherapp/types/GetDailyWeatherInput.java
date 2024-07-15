package com.proit.weatherapp.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
public class GetDailyWeatherInput extends AbstractInput {
    @Serial
    private static final long serialVersionUID = 1L;

    private Double latitude;
    private Double longitude;
    private String timezone;
}
