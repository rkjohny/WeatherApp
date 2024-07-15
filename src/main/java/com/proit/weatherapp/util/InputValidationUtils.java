package com.proit.weatherapp.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public final class InputValidationUtils {

    private InputValidationUtils() {
    }

    public static void validateId(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Id is null or negative");
        }
    }

    public static void validateObject(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Invalid argument");
        }
    }

    public static void validateList(List list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List is null or size is 0");
        }
    }

    public static void validateString(String s) {
        if (StringUtils.isBlank(s)) {
            throw new IllegalArgumentException("Invalid argument");
        }
    }
}
