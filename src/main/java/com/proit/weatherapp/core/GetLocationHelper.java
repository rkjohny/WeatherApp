package com.proit.weatherapp.core;

import com.proit.weatherapp.services.LocationService;
import com.proit.weatherapp.dto.types.GetLocationInput;
import com.proit.weatherapp.dto.types.GetLocationOutput;
import com.proit.weatherapp.util.Sanitizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class GetLocationHelper extends AbstractHelper <GetLocationInput, GetLocationOutput> {
    private final LocationService locationService;


    @Override
    protected void validateInput(GetLocationInput input, Object... args) {
        if (!StringUtils.isBlank(input.getCity())) {
            input.setCity(Sanitizer.sanitize(input.getCity().trim()));
        }
        if (!StringUtils.isBlank(input.getFilter())) {
            input.setFilter(Sanitizer.sanitize(input.getFilter().trim()));
        }
    }

    @Override
    protected void checkPermission(GetLocationInput input, Object... args) {

    }

    @Override
    protected GetLocationOutput executeHelper(GetLocationInput input, Object... args) {
        GetLocationOutput output = new GetLocationOutput();
        output.setLocations(locationService.getLocations(input.getCity(), input.getFilter()));
        return output;
    }
}
