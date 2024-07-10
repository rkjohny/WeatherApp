package com.proit.weatherapp.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.proit.weatherapp.core.UserService;
import com.proit.weatherapp.dto.Location;
import com.proit.weatherapp.entity.FavouriteLocation;
import com.proit.weatherapp.entity.User;
import com.proit.weatherapp.security.Authority;
import com.proit.weatherapp.core.OpenMetroService;
import com.proit.weatherapp.core.JsonService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RolesAllowed(value = Authority.USER)
public class LocationService {
    private static Logger logger = LoggerFactory.getLogger(LocationService.class);

    private final OpenMetroService openMetroService;
    private final JsonService jsonService;
    private final UserService userService;

    public LocationService(OpenMetroService openMetroService, JsonService jsonService, UserService userService) {
        this.openMetroService = openMetroService;
        this.jsonService = jsonService;
        this.userService = userService;
    }

    @NotNull
    public List<Location> getLocations(String city, String filterText) {
        List<Location> locations = new ArrayList<>();

        if (!StringUtils.isBlank(city)) {
            String response = openMetroService.getLocationsByName(city);
            JsonNode results = jsonService.getProperty(response, "results");
            if (results != null) {
                locations.addAll(jsonService.fromJsonToList(results, Location.class));
            }
            if (!StringUtils.isBlank(filterText)) {
                List<Location> filtered = locations.stream().filter(location -> location.getName().equalsIgnoreCase(filterText)).toList();
                locations.clear();
                locations.addAll(filtered);
            }
        }
        return locations;
    }

    public void checkFavorite(Location location) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!StringUtils.isBlank(username)) {
            Optional<User> user = userService.getByUsername(username);
            user.ifPresent(u -> checkFavorite(u, location));
        }
    }

    public void checkFavorite(User user, Location location) {
        boolean favorite = user.getFavouriteLocations().stream().anyMatch(fu -> Objects.equals(fu.getLocationId(), location.getId()));
        location.setFavorite(favorite);
    }

    public void toggleFavorite(Location location) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!StringUtils.isBlank(username)) {
            Optional<User> user = userService.getByUsername(username);
            user.ifPresent(u -> toggleFavorite(u, location));
        }
    }

    public void toggleFavorite(User user, Location location) {
        checkFavorite(user, location);
        if (!location.isFavorite()) {
            user.getFavouriteLocations().add(FavouriteLocation.fromLocation(user, location));
            location.setFavorite(true);
        } else {
            boolean removed = user.getFavouriteLocations().removeIf(fv -> Objects.equals(fv.getLocationId(), location.getId()));
            location.setFavorite(!removed);
        }
        userService.save(user);
    }

    public List<Location> getFavoriteLocations() {
        List<Location> locations = new ArrayList<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!StringUtils.isBlank(username)) {
            Optional<User> user = userService.getByUsername(username);
            user.ifPresent(u -> {
                var favoriteLocations = u.getFavouriteLocations();
                favoriteLocations.forEach(fv -> locations.add(fv.toLocation()));
            });
        }
        return locations;
    }
}
