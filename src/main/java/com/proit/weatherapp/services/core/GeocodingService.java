package com.proit.weatherapp.services.core;


import com.proit.weatherapp.error.GeocodingException;
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
    private static final String LOCATION_URL = "https://geocoding-api.open-meteo.com/v1/search"; // ?name=Dhaka&count=10&language=en&format=json
    public static final int MAX_RESULT = 100;

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
                .queryParam("name", city)
                .queryParam("count", MAX_RESULT)
                .queryParam("language", "en")
                .queryParam("format", "json");
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
