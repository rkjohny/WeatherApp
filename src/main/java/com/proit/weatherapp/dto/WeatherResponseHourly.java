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
public class WeatherResponseHourly implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @JsonProperty(value = "time")
    private List<String> time = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.TEMPERATURE_2M)
    private List<Double> temperature = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.WIND_10M)
    private List<Double> wind = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.RAIN)
    private List<Double> rain = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.SHOWERS)
    private List<Double> showers = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.SNOWFALL)
    private List<Double> snowfall = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.PRECIPITATION)
    private List<Double> precipitation = new ArrayList<>();

    @JsonProperty(value = OpenMetroAPIParamValue.PRECIPITATION_PROB)
    private List<Double> probability = new ArrayList<>();



    @NotNull
    public List<WeatherDataHourly> getHourlyList() {
        List<WeatherDataHourly> list = new ArrayList<>();
        Optional<Integer> minValue = Stream.of(time.size(), temperature.size(), rain.size(), wind.size(),
                        precipitation.size(), probability.size()).min(Integer::compareTo);

        minValue.ifPresent(length -> IntStream.range(0, length).forEach(i -> {
            WeatherDataHourly weatherDataHourly = new WeatherDataHourly();
            weatherDataHourly.setTime(Utils.checkNull(time.get(i)));
            weatherDataHourly.setTemperature(Utils.checkNull(temperature.get(i)));
            weatherDataHourly.setWind(Utils.checkNull(wind.get(i)));
            weatherDataHourly.setRain(Utils.checkNull(rain.get(i)));
            weatherDataHourly.setPrecipitation(Utils.checkNull(precipitation.get(i)));
            weatherDataHourly.setProbability(Utils.checkNull(probability.get(i)));
            weatherDataHourly.setPrecipitation(Utils.checkNull(precipitation.get(i)));
            weatherDataHourly.setProbability(Utils.checkNull(probability.get(i)));
            weatherDataHourly.setShowers(Utils.checkNull(showers.get(i)));
            weatherDataHourly.setSnowfall(Utils.checkNull(snowfall.get(i)));
            list.add(weatherDataHourly);
        }));
        return list;
    }
}
