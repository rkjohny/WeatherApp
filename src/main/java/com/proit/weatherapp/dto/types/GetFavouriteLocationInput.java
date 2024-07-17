package com.proit.weatherapp.dto.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
public class GetFavouriteLocationInput extends AbstractInput {
    @Serial
    private static final long serialVersionUID = 1L;
}
