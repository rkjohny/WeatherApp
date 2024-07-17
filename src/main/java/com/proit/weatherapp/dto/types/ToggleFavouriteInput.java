package com.proit.weatherapp.dto.types;

import com.proit.weatherapp.dto.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
public class ToggleFavouriteInput extends AbstractInput {
    @Serial
    private static final long serialVersionUID = 1L;

    private Location location;
}
