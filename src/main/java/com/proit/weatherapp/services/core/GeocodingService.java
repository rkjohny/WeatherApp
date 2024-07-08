package com.proit.weatherapp.services.core;


import com.proit.weatherapp.error.GeocodingException;
import com.proit.weatherapp.types.GeocodingParamValue;
import com.proit.weatherapp.types.LocationParam;
import com.proit.weatherapp.types.TemperatureDailyParam;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.MessageFormat;
import java.util.List;



@Component
public class GeocodingService {
    private static final String LOCATION_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private static final String TEMPERATURE_DAILY_URL = "https://api.open-meteo.com/v1/forecast";

    public static final int MAX_LOCATION_RESULT = 100;
    public static final int MAX_DAILY_FORECAST_RESULT = 16;

    private final RestTemplate restTemplate;

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private void checkResponse(ResponseEntity response, URI uri, HttpStatus status) {
        if (response.getStatusCode() != status) {
            throw new GeocodingException(MessageFormat.format("Failed calling API {0} Response Code: {1}", uri.getPath(), response.getStatusCode()));
        }
    }

    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }


    @NotNull
    public String getLocationsByCity(String city) {
        // Build URI with query parameters
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(LOCATION_URL)
                .queryParam(LocationParam.NAME, city)
                .queryParam(LocationParam.COUNT, MAX_LOCATION_RESULT)
                .queryParam(LocationParam.LANGUAGE, "en")
                .queryParam(LocationParam.FORMAT, "json");
        URI uri = uriBuilder.build().toUri();

        // Create the HttpEntity object with headers
        HttpEntity<String> entity = new HttpEntity<>(generateHeaders());

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        checkResponse(response, uri, HttpStatus.OK);
        String jsonResponse = response.getBody();
        return StringUtils.isBlank(jsonResponse) ? StringUtils.EMPTY : jsonResponse;
    }


    public String getTemperatureDaily(Double latitude, Double longitude, String timeZone) {
        String dailyPram = String.join(",", GeocodingParamValue.Daily.TEMP_2M_MAX, GeocodingParamValue.Daily.TEMP_2M_MIN, GeocodingParamValue.Daily.RAIN_SUM, GeocodingParamValue.Daily.WIND_10M_MAX);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(LOCATION_URL)
                .queryParam(TemperatureDailyParam.LATITUDE, latitude)
                .queryParam(TemperatureDailyParam.LATITUDE, longitude)
                .queryParam(TemperatureDailyParam.TIME_ZONE, timeZone)
                .queryParam(TemperatureDailyParam.FORECAST_DAY, MAX_DAILY_FORECAST_RESULT)
                .queryParam(TemperatureDailyParam.DAILY, dailyPram)
                .queryParam(TemperatureDailyParam.FORMAT, "json");
        URI uri = uriBuilder.build().toUri();

        // Create the HttpEntity object with headers
        HttpEntity<String> entity = new HttpEntity<>(generateHeaders());

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        checkResponse(response, uri, HttpStatus.OK);
        String jsonResponse = response.getBody();
        return StringUtils.isBlank(jsonResponse) ? StringUtils.EMPTY : jsonResponse;
    }
}
