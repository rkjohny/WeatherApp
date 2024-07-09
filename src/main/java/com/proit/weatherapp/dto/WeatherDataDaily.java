package com.proit.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class WeatherDataDaily implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String date;
    private Double maxTemperature;
    private Double minTemperature;
    private Double rain;
    private Double maxWind;
    private Double precipitation;
    private Double probability;
    private Double showers;
    private Double snowfall;
}
