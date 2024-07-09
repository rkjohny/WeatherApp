package com.proit.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proit.weatherapp.types.OpenMetroAPIParamValue;
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
public class TemperatureHourlyResponse implements Serializable {
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
    public List<TemperatureHourly> getHourlyList() {
        List<TemperatureHourly> list = new ArrayList<>();
        Optional<Integer> minValue = Stream.of(time.size(), temperature.size(), rain.size(), wind.size(),
                        precipitation.size(), probability.size()).min(Integer::compareTo);

        minValue.ifPresent(length -> IntStream.range(0, length).forEach(i -> {
            TemperatureHourly temperatureHourly = new TemperatureHourly();
            temperatureHourly.setTime(Utils.checkNull(time.get(i)));
            temperatureHourly.setTemperature(Utils.checkNull(temperature.get(i)));
            temperatureHourly.setWind(Utils.checkNull(wind.get(i)));
            temperatureHourly.setRain(Utils.checkNull(rain.get(i)));
            temperatureHourly.setPrecipitation(Utils.checkNull(precipitation.get(i)));
            temperatureHourly.setProbability(Utils.checkNull(probability.get(i)));
            temperatureHourly.setPrecipitation(Utils.checkNull(precipitation.get(i)));
            temperatureHourly.setProbability(Utils.checkNull(probability.get(i)));
            temperatureHourly.setShowers(Utils.checkNull(showers.get(i)));
            temperatureHourly.setSnowfall(Utils.checkNull(snowfall.get(i)));
            list.add(temperatureHourly);
        }));
        return list;
    }
}
