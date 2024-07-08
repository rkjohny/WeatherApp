package com.proit.weatherapp.error;

public class GeocodingException extends RuntimeException {

    public GeocodingException() {
    }

    public GeocodingException(String message) {
        super(message);
    }
}
