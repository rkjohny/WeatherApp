package com.proit.weatherapp.types;

public final class GeocodingParamValue {
    private GeocodingParamValue() {
    }

    public static final class Location {
        private Location() {
        }
    }

    public static final class Daily {
        private Daily() {
        }

        public static final String TEMP_2M_MAX = "temperature_2m_max";
        public static final String TEMP_2M_MIN = "temperature_2m_min";
        public static final String RAIN_SUM = "rain_sum";
        public static final String WIND_10M_MAX = "wind_speed_10m_max";
    }
}
