package com.proit.weatherapp.api;

import com.proit.weatherapp.dto.Location;
import com.proit.weatherapp.dto.types.GetLocationInput;
import com.proit.weatherapp.dto.types.GetLocationOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class LocationApiTest {
    @Autowired
    private LocationApi locationApi;

    @Test
    public void getLocation() {
        GetLocationInput input = new GetLocationInput();
        input.setCity("Dhaka");
        input.setFilter("Dhaka");

        GetLocationOutput output = locationApi.getLocations(input);
        List<Location> locations = output.getLocations();
        assertFalse(locations.isEmpty());
        assertEquals("dhaka", locations.get(0).getName().toLowerCase());
    }
}
