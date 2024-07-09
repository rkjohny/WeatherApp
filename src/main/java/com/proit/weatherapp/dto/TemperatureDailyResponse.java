package com.proit.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TemperatureDailyResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @JsonProperty(value = "time")
    private List<String> date = new ArrayList<>();

    @JsonProperty(value = "temperature_2m_max")
    private List<Double> tempMax = new ArrayList<>();

    @JsonProperty(value = "temperature_2m_min")
    private List<Double> tempMin = new ArrayList<>();

    @JsonProperty(value = "rain_sum")
    private List<Double> rain = new ArrayList<>();

    @JsonProperty(value = "wind_speed_10m_max")
    private List<Double> wind = new ArrayList<>();



    @NotNull
    public List<TemperatureDaily> getDailyList() {
        List<TemperatureDaily> list = new ArrayList<>();
        Optional<Integer> minValue = Stream.of(date.size(), tempMax.size(), tempMin.size(), rain.size(), wind.size()).min(Integer::compareTo);

        minValue.ifPresent(length -> IntStream.range(0, length).forEach(i -> {
            TemperatureDaily temperatureDaily = new TemperatureDaily();
            temperatureDaily.setDate(Utils.checkNull(date.get(i)));
            temperatureDaily.setMaxTemperature(Utils.checkNull(tempMax.get(i)));
            temperatureDaily.setMinTemperature(Utils.checkNull(tempMin.get(i)));
            temperatureDaily.setRain(Utils.checkNull(rain.get(i)));
            temperatureDaily.setMaxWind(Utils.checkNull(wind.get(i)));
            list.add(temperatureDaily);
        }));
        return list;
    }
}
