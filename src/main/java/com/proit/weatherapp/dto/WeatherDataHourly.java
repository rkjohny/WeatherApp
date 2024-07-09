package com.proit.weatherapp.dto;

import com.proit.weatherapp.config.Constant;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Getter
@Setter
public class WeatherDataHourly implements Serializable {
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


    public static long toEpochMilli(WeatherDataHourly weatherDataHourly) {
        String dateTimeString = weatherDataHourly.getTime();
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, Constant.APP_DATE_TIME_FORMATTER);
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
