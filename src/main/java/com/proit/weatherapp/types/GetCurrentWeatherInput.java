package com.proit.weatherapp.types;

import com.proit.weatherapp.dto.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetCurrentWeatherInput extends AbstractInput {
    @Serial
    private static final long serialVersionUID = 1L;

    List<Location> locations = new ArrayList<>();
}
