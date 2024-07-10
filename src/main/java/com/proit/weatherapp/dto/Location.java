package com.proit.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proit.weatherapp.util.Utils;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class Location implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
    private boolean favorite = false;

    @NotNull
    public String getCountry() {
        // This method is used in comparator, returning empty string instead of null to avoid NullPointerException
        return Utils.checkNull(country);
    }

}
