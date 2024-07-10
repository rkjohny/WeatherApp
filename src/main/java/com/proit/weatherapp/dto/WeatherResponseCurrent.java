package com.proit.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.types.OpenMetroAPIParam;
import com.proit.weatherapp.types.OpenMetroAPIParamValue;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
public class WeatherResponseCurrent implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "time")
    private String dateTime;

    @JsonProperty(value = OpenMetroAPIParamValue.TEMPERATURE_2M)
    private Double temperature;

    @JsonProperty(value = OpenMetroAPIParamValue.WIND_10M)
    private Double wind;

    @JsonProperty(value = OpenMetroAPIParamValue.WIND_DIRECTION_10M)
    private Long windDirection;

    @JsonProperty(value = OpenMetroAPIParamValue.RAIN)
    private Double rain;

    @JsonProperty(value = OpenMetroAPIParamValue.SHOWERS)
    private Double showers;

    @JsonProperty(value = OpenMetroAPIParamValue.SNOWFALL)
    private Double snowfall;

    @JsonProperty(value = OpenMetroAPIParamValue.PRECIPITATION)
    private Double precipitation;

    @JsonProperty(value = OpenMetroAPIParamValue.PRECIPITATION_PROB)
    private Double probability;

    @JsonProperty(value = OpenMetroAPIParamValue.CLOUD_COVER)
    private Long cloud;

    @JsonProperty(value = OpenMetroAPIParamValue.CLOUD_COVER_HIGH)
    private Long cloudHigh;

    @JsonProperty(value = OpenMetroAPIParamValue.CLOUD_COVER_MID)
    private Long cloudMid;

    @JsonProperty(value = OpenMetroAPIParamValue.CLOUD_COVER_LOW)
    private Long cloudLow;

    @JsonProperty(value = OpenMetroAPIParamValue.WEATHER_CODE)
    private Long weatherCode;

    public WeatherDataCurrent toCurrentData(Location location) {
        WeatherDataCurrent current = new WeatherDataCurrent();

        //TODO: get this data from response
        current.setLatitude(location.getLatitude());
        current.setLongitude(location.getLongitude());
        current.setTimezone(location.getTimezone());
        current.setCity(location.getName());
        current.setCountry(location.getCountry());
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, Constant.APP_DATE_TIME_FORMATTER);
        current.setDate(localDateTime.format(Constant.APP_DATE_FORMATTER));

        current.setTemperature(temperature);
        current.setWind(wind);
        current.setWindDirection(windDirection);
        current.setRain(rain);
        current.setShowers(showers);
        current.setSnowfall(snowfall);
        current.setPrecipitation(precipitation);
        current.setProbability(probability);
        current.setCloud(cloud);
        current.setCloudHigh(cloudHigh);
        current.setCloudMid(cloudMid);
        current.setCloudLow(cloudLow);
        current.setWeatherCode(weatherCode);
        return current;
    }
}
