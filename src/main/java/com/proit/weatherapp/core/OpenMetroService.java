package com.proit.weatherapp.core;


import com.proit.weatherapp.error.GeocodingException;
import com.proit.weatherapp.dto.types.OpenMetroAPIParamValue;
import com.proit.weatherapp.dto.types.OpenMetroAPIParam;
import com.proit.weatherapp.util.Utils;
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
public class OpenMetroService {
    private static final String LOCATION_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private static final String TEMPERATURE_URL = "https://api.open-meteo.com/v1/forecast";

    public static final int MAX_LOCATION_RESULT = 100;
    public static final int MAX_DAILY_FORECAST_RESULT = 16;
    public static final int MAX_HOURLY_FORECAST_RESULT = 24;

    private final RestTemplate restTemplate;

    public OpenMetroService(RestTemplate restTemplate) {
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
    public String getLocationsByName(String name) {
        // Build URI with query parameters
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(LOCATION_URL)
                .queryParam(OpenMetroAPIParam.NAME, name)
                .queryParam(OpenMetroAPIParam.COUNT, MAX_LOCATION_RESULT)
                .queryParam(OpenMetroAPIParam.LANGUAGE, OpenMetroAPIParamValue.LANGUAGE_EN)
                .queryParam(OpenMetroAPIParam.FORMAT, OpenMetroAPIParamValue.FORMAT_JSON);
        URI uri = uriBuilder.build().toUri();

        // Create the HttpEntity object with headers
        HttpEntity<String> entity = new HttpEntity<>(generateHeaders());

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        checkResponse(response, uri, HttpStatus.OK);
        String jsonResponse = response.getBody();
        return Utils.checkNull(jsonResponse);
    }


    @NotNull
    public String getWeatherDaily(Double latitude, Double longitude, String timeZone) {
        String dailyPram = String.join(",", OpenMetroAPIParamValue.TEMPERATURE_2M_MAX, OpenMetroAPIParamValue.TEMPERATURE_2M_MIN,
                OpenMetroAPIParamValue.RAIN_SUM, OpenMetroAPIParamValue.WIND_10M_MAX, OpenMetroAPIParamValue.PRECIPITATION_SUM,
                OpenMetroAPIParamValue.PRECIPITATION_PROB_MEAM, OpenMetroAPIParamValue.SHOWERS_SUM, OpenMetroAPIParamValue.SNOWFALL_SUM);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(TEMPERATURE_URL)
                .queryParam(OpenMetroAPIParam.LATITUDE, latitude)
                .queryParam(OpenMetroAPIParam.LONGITUDE, longitude)
                .queryParam(OpenMetroAPIParam.TIME_ZONE, timeZone)
                .queryParam(OpenMetroAPIParam.FORECAST_DAYS, MAX_DAILY_FORECAST_RESULT)
                .queryParam(OpenMetroAPIParam.DAILY, dailyPram)
                .queryParam(OpenMetroAPIParam.FORMAT, OpenMetroAPIParamValue.FORMAT_JSON);
        URI uri = uriBuilder.build().toUri();

        // Create the HttpEntity object with headers
        HttpEntity<String> entity = new HttpEntity<>(generateHeaders());

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        checkResponse(response, uri, HttpStatus.OK);
        String jsonResponse = response.getBody();
        return Utils.checkNull(jsonResponse);
    }


    @NotNull
    public String getWeatherHourly(Double latitude, Double longitude, String timeZone, String date) {
        String hourlyPram = String.join(",", OpenMetroAPIParamValue.TEMPERATURE_2M, OpenMetroAPIParamValue.WIND_10M,
                OpenMetroAPIParamValue.RAIN, OpenMetroAPIParamValue.PRECIPITATION, OpenMetroAPIParamValue.PRECIPITATION_PROB,
                OpenMetroAPIParamValue.SHOWERS, OpenMetroAPIParamValue.SNOWFALL);

        if (StringUtils.isBlank(date)) {
            date = Utils.getCurrentDate(timeZone);
        }
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(TEMPERATURE_URL)
                .queryParam(OpenMetroAPIParam.LATITUDE, latitude)
                .queryParam(OpenMetroAPIParam.LONGITUDE, longitude)
                .queryParam(OpenMetroAPIParam.TIME_ZONE, timeZone)
                .queryParam(OpenMetroAPIParam.HOURLY, hourlyPram)
                .queryParam(OpenMetroAPIParam.START_DATE, date)
                .queryParam(OpenMetroAPIParam.END_DATE, date)
                .queryParam(OpenMetroAPIParam.FORMAT, OpenMetroAPIParamValue.FORMAT_JSON);
        URI uri = uriBuilder.build().toUri();

        // Create the HttpEntity object with headers
        HttpEntity<String> entity = new HttpEntity<>(generateHeaders());

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        checkResponse(response, uri, HttpStatus.OK);
        String jsonResponse = response.getBody();
        return Utils.checkNull(jsonResponse);
    }

    @NotNull
    public String getWeatherCurrent(Double latitude, Double longitude, String timeZone) {
        String currentPram = String.join(",", OpenMetroAPIParamValue.TEMPERATURE_2M, OpenMetroAPIParamValue.WIND_10M,
                OpenMetroAPIParamValue.WIND_DIRECTION_10M, OpenMetroAPIParamValue.RAIN, OpenMetroAPIParamValue.PRECIPITATION,
                OpenMetroAPIParamValue.PRECIPITATION_PROB, OpenMetroAPIParamValue.SHOWERS, OpenMetroAPIParamValue.SNOWFALL,
                OpenMetroAPIParamValue.CLOUD_COVER, OpenMetroAPIParamValue.CLOUD_COVER_HIGH, OpenMetroAPIParamValue.CLOUD_COVER_MID,
                OpenMetroAPIParamValue.CLOUD_COVER_LOW, OpenMetroAPIParamValue.WEATHER_CODE);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(TEMPERATURE_URL)
                .queryParam(OpenMetroAPIParam.LATITUDE, latitude)
                .queryParam(OpenMetroAPIParam.LONGITUDE, longitude)
                .queryParam(OpenMetroAPIParam.TIME_ZONE, timeZone)
                .queryParam(OpenMetroAPIParam.CURRENT, currentPram)
                .queryParam(OpenMetroAPIParam.FORMAT, OpenMetroAPIParamValue.FORMAT_JSON);
        URI uri = uriBuilder.build().toUri();

        // Create the HttpEntity object with headers
        HttpEntity<String> entity = new HttpEntity<>(generateHeaders());

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        checkResponse(response, uri, HttpStatus.OK);
        String jsonResponse = response.getBody();
        return Utils.checkNull(jsonResponse);
    }
}
