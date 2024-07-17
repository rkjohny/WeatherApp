package com.proit.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CachedData {
    private String city;
    private Double latitude;
    private Double longitude;
    private String timezone;
    private String date;
    private String country;
}
