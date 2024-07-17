package com.proit.weatherapp.dto.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AbstractOutput implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
//    private String result = "OK";
}
