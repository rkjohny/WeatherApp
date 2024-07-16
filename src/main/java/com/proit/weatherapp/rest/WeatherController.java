package com.proit.weatherapp.rest;


import com.proit.weatherapp.api.WeatherApi;
import com.proit.weatherapp.types.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weathers")
@PermitAll
public class WeatherController {
    private final WeatherApi weatherApi;


    @Operation(summary = "Get daily weather forecast (of 16 days) of a location specified by latitude, longitude and the timezone")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "object that contains the latitude, longitude and timezone of a location whose daily weather forecast will be returned", required = true,
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetDailyWeatherInput.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object which contains list of WeatherDataDaily object that contains the daily forecast data",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetDailyWeatherOutput.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping(value = "/daily", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetDailyWeatherOutput> getDailyWeather(@Valid @RequestBody GetDailyWeatherInput input) {
        GetDailyWeatherOutput output = weatherApi.getDailyWeather(input);
        return ResponseEntity.ok(output);
    }


    @Operation(summary = "Get hourly weather forecast (of 24 hours) of a specific day of a location specified by latitude, longitude and the timezone")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "object that contains the latitude, longitude, timezone " +
            "of a location whose hourly (for 24 hours) weather forecast will be returned of the date specified. " +
            "If date is null api will return the hourly forecast of current day", required = true,
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetHourlyWeatherInput.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object which contains list of WeatherDataDaily object that contains the hourly forecast data",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetHourlyWeatherOutput.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping(value = "/hourly", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetHourlyWeatherOutput> getHourlyWeather(@Valid @RequestBody GetHourlyWeatherInput input) {
        GetHourlyWeatherOutput output = new GetHourlyWeatherOutput();
        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Get the current weather information of a list of locations")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "object that contains the list of location whose current weather forecast will be returned", required = true,
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetCurrentWeatherInput.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object which contains list of WeatherDataCurrent object that contains the current weather forecast data",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetCurrentWeatherOutput.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping(value = "/favourite", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCurrentWeatherOutput> getCurrentWeather(@Valid @RequestBody GetCurrentWeatherInput input) {
        GetCurrentWeatherOutput output = weatherApi.getCurrentWeather(input);
        return ResponseEntity.ok(output);
    }

}
