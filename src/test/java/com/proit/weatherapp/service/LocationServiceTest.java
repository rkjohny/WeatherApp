package com.proit.weatherapp.service;

import com.proit.weatherapp.dto.Location;
import com.proit.weatherapp.services.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class LocationServiceTest {
    @Autowired
    LocationService locationService;


    @BeforeEach
    public void initTest() {

    }

    @Test
    void getByName() {
        List<Location> locationList = locationService.getLocations("Dhaka", "dhaka");
        Assertions.assertFalse(locationList.isEmpty());
        locationList.forEach(location -> assertEquals("Dhaka", location.getName()));
    }
}
