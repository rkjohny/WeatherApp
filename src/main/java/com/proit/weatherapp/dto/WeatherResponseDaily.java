package com.proit.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proit.weatherapp.dto.types.OpenMetroAPIParamValue;
import com.proit.weatherapp.util.Utils;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Getter
@Setter
public class WeatherResponseDaily implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @JsonProperty(value = "time")
    private List<String> date = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.TEMPERATURE_2M_MAX)
    private List<Double> tempMax = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.TEMPERATURE_2M_MIN)
    private List<Double> tempMin = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.RAIN_SUM)
    private List<Double> rain = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.WIND_10M_MAX)
    private List<Double> wind = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.PRECIPITATION_SUM)
    private List<Double> precipitation = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.PRECIPITATION_PROB_MEAM)
    private List<Double> probability = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.SHOWERS_SUM)
    private List<Double> showers = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.SNOWFALL_SUM)
    private List<Double> snowfall = new ArrayList<>();

    @NotNull
    public List<WeatherDataDaily> getDailyList() {
        List<WeatherDataDaily> list = new ArrayList<>();
        Optional<Integer> minValue = Stream.of(date.size(), tempMax.size(), tempMin.size(), rain.size(), wind.size()).min(Integer::compareTo);

        minValue.ifPresent(length -> IntStream.range(0, length).forEach(i -> {
            WeatherDataDaily weatherDataDaily = new WeatherDataDaily();
            weatherDataDaily.setDate(Utils.checkNull(date.get(i)));
            weatherDataDaily.setMaxTemperature(Utils.checkNull(tempMax.get(i)));
            weatherDataDaily.setMinTemperature(Utils.checkNull(tempMin.get(i)));
            weatherDataDaily.setRain(Utils.checkNull(rain.get(i)));
            weatherDataDaily.setMaxWind(Utils.checkNull(wind.get(i)));
            weatherDataDaily.setPrecipitation(Utils.checkNull(precipitation.get(i)));
            weatherDataDaily.setProbability(Utils.checkNull(probability.get(i)));
            weatherDataDaily.setShowers(Utils.checkNull(showers.get(i)));
            weatherDataDaily.setSnowfall(Utils.checkNull(snowfall.get(i)));
            list.add(weatherDataDaily);
        }));
        return list;
    }
}
