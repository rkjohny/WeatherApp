package com.proit.weatherapp.dto.types;

public final class OpenMetroAPIParamValue {
    private OpenMetroAPIParamValue() {
    }

    // Values for 'language' param
    public static final String LANGUAGE_EN = "en";

    // Values for 'format' param
    public static final String FORMAT_JSON = "json";

    // Values for 'daily' param
    public static final String TEMPERATURE_2M_MAX = "temperature_2m_max";
    public static final String TEMPERATURE_2M_MIN = "temperature_2m_min";
    public static final String RAIN_SUM = "rain_sum";
    public static final String WIND_10M_MAX = "wind_speed_10m_max";
    public static final String PRECIPITATION_SUM = "precipitation_sum";
    public static final String PRECIPITATION_PROB_MEAM = "precipitation_probability_mean";
    public static final String SHOWERS_SUM = "showers_sum";
    public static final String SNOWFALL_SUM = "snowfall_sum";

    // Values for 'hourly' and 'current' param
    public static final String TEMPERATURE_2M = "temperature_2m";
    public static final String WIND_10M = "wind_speed_10m";
    public static final String WIND_DIRECTION_10M = "wind_direction_10m";
    public static final String RAIN = "rain";
    public static final String PRECIPITATION = "precipitation";
    public static final String PRECIPITATION_PROB = "precipitation_probability";
    public static final String SHOWERS = "showers";
    public static final String SNOWFALL = "snowfall";
    public static final String CLOUD_COVER = "cloud_cover";
    public static final String CLOUD_COVER_LOW = "cloud_cover_low";
    public static final String CLOUD_COVER_MID = "cloud_cover_mid";
    public static final String CLOUD_COVER_HIGH = "cloud_cover_high";
    public static final String WEATHER_CODE = "weather_code";
}
