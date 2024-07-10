package com.proit.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
public class WeatherDataCurrent implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Double latitude;
    private Double longitude;
    private String city;
    private String timezone;
    private String country;
    private String date;

    private Double temperature;

    private Double wind;

    private Long windDirection;

    private Double rain;

    private Double showers;

    private Double snowfall;

    private Double precipitation;

    private Double probability;

    private Long cloud;

    private Long cloudHigh;

    private Long cloudMid;

    private Long cloudLow;

    private Long weatherCode;
}
