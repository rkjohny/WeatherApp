package com.proit.weatherapp.rest;


import com.proit.weatherapp.api.LocationApi;
import com.proit.weatherapp.types.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {
    private final LocationApi locationApi;

    @Operation(summary = "Get all the location by a city filtered by a filter text")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Object that holds the name of the location and the filter text", required = true,
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetLocationInput.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object that holds the list of the location",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetLocationOutput.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping(value = "/by-name", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetLocationOutput> getLocations(@Valid @RequestBody GetLocationInput input) {
        GetLocationOutput output = locationApi.getLocations(input);
        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Check if a location has been marked as favorite by the current logged-in user")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object that hold the location to check", required = true,
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CheckFavouriteInput.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "object that holds a boolean member favourite which indicates whether the location has been marked as favorite",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CheckFavouriteOutput.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping(value = "/is-favourite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CheckFavouriteOutput> checkFavorite(@Valid @RequestBody CheckFavouriteInput input) {
        CheckFavouriteOutput output = locationApi.checkFavorite(input);
        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Toggle the favorite mark of a location by the current logged-in user")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object that holds the Location object to toggle", required = true,
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ToggleFavouriteInput.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Object that holds a member favourite which will indicate if the location is marked as favourite after toggling",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ToggleFavouriteOutput.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping(value = "/toggle-favourite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ToggleFavouriteOutput> toggleFavorite(@Valid @RequestBody ToggleFavouriteInput input) {
        ToggleFavouriteOutput output = locationApi.toggleFavorite(input);
        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Get all the locations marked as favorite by the current logged-in user")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object", required = true,
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetFavouriteLocationInput.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object that holds the list of the locations marked as favorite",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetFavouriteLocationOutput.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping(value = "/get-favourite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetFavouriteLocationOutput> getFavoriteLocations(@Valid @RequestBody GetFavouriteLocationInput input) {
        GetFavouriteLocationOutput output = locationApi.getFavoriteLocations(input);
        return ResponseEntity.ok(output);
    }
}
