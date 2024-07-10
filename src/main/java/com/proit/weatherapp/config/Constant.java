package com.proit.weatherapp.config;

import java.time.format.DateTimeFormatter;

public final class Constant {
    private Constant() {
    }

    public static final String SELECTED_LOCATION_KEY = "SELECTED_LOCATION_KEY";
    public static final String SELECTED_DAILY_WEATHER_KEY = "SELECTED_DAILY_WEATHER_KEY";
    public static final String SELECTED_CURRENT_WEATHER_KEY = "SELECTED_CURRENT_WEATHER_KEY";

    public static final String APP_ISO8601_TIME_FORMAT = "HH:mm";
    public static final String APP_ISO8601_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

    public static final DateTimeFormatter APP_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter APP_TIME_FORMATTER = DateTimeFormatter.ofPattern(APP_ISO8601_TIME_FORMAT);
}
