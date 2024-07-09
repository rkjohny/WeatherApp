package com.proit.weatherapp.types;

public final class OpenMetroAPIParam {
    private OpenMetroAPIParam() {
    }

    // Common params
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String TIME_ZONE = "timezone";
    public static final String FORMAT = "format";

    // Location (Geocoding) API params
    public static final String NAME = "name";
    public static final String COUNT = "count";
    public static final String LANGUAGE = "language";

    // Daily temperature forecast API params
    public static final String DAILY = "daily";
    public static final String FORECAST_DAY = "forecast_days";

    // Hourly temperature forecast API params
    public static final String HOURLY = "hourly";

}
