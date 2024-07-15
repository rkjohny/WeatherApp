package com.proit.weatherapp.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
public class GetLocationInput extends AbstractInput {
    @Serial
    private static final long serialVersionUID = 1L;

    private String city;
    private String filter;
}
