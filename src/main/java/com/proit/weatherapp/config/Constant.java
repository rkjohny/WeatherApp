package com.proit.weatherapp.config;

import java.time.format.DateTimeFormatter;

public final class Constant {
    private Constant() {
    }

    public static final String APP_CACHE_DATA = "APP_CACHE_DATA";

    public static final String APP_TIME_FORMAT_SHORT = "HH:mm";

    public static final DateTimeFormatter APP_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter APP_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static final DateTimeFormatter APP_TIME_FORMATTER_SHORT = DateTimeFormatter.ofPattern(APP_TIME_FORMAT_SHORT);
    public static final DateTimeFormatter APP_TIME_FORMATTER_FULL = DateTimeFormatter.ISO_LOCAL_TIME;
}
