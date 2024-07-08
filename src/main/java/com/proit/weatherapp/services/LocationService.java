package com.proit.weatherapp.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.proit.weatherapp.dto.Location;
import com.proit.weatherapp.security.Authority;
import com.proit.weatherapp.services.core.GeocodingService;
import com.proit.weatherapp.services.core.JsonHelper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RolesAllowed(value = Authority.USER)
public class LocationService {
    private static Logger logger = LoggerFactory.getLogger(LocationService.class);

    private final GeocodingService geocodingService;
    private final JsonHelper jsonHelper;

    public LocationService(GeocodingService geocodingService, JsonHelper jsonHelper) {
        this.geocodingService = geocodingService;
        this.jsonHelper = jsonHelper;
    }

    @NotNull
    public List<Location> getLocations(String city, String filterText) {
        List<Location> locations = new ArrayList<>();

        if (!StringUtils.isBlank(city)) {
            String response = geocodingService.getLocationsByCity(city);
            JsonNode results = jsonHelper.getProperty(response, "results");
            if (results != null) {
                locations = jsonHelper.fromJsonToList(results, Location.class);
            }
            if (!StringUtils.isBlank(filterText) && !locations.isEmpty()) {
                locations = locations.stream().filter(location -> location.getName().equalsIgnoreCase(filterText)).toList();
            }
        }
        return locations;
    }
}
