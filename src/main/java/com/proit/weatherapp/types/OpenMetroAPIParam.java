package com.proit.weatherapp.types;

public final class OpenMetroAPIParam {
    private OpenMetroAPIParam() {
    }

    // Weather params
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String TIME_ZONE = "timezone";
    public static final String FORMAT = "format";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";

    // Location (Geocoding) API params
    public static final String NAME = "name";
    public static final String COUNT = "count";
    public static final String LANGUAGE = "language";

    // Daily weather forecast API params
    public static final String DAILY = "daily";
    public static final String FORECAST_DAYS = "forecast_days";

    // Hourly weather forecast API params
    public static final String HOURLY = "hourly";
    public static final String FORECAST_HOURS = "forecast_hours";

}
