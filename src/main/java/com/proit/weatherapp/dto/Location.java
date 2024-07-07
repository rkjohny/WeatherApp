package com.proit.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Location {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Long elevation;
    @JsonProperty(value = "country_code")
    private String countryCode;
    private String country;
    private String admin1;
    private String admin2;
    private String admin3;
    private String admin4;
    private String timezone;
}
