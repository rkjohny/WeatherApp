package com.proit.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class TemperatureHourly implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String time;
    private Double temperature;
    private Double rain;
    private Double wind;
    private Double precipitation;
    private Double probability;
    private Double showers;
    private Double snowfall;
}
