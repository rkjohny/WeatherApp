package com.proit.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TemperatureDaily implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String date;
    private Double temperatureMax;
    private Double temperatureMin;
    private Double rain;
    private Double windMax;
}
