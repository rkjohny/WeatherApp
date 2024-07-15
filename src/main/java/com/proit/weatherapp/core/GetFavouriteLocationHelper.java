package com.proit.weatherapp.core;

import com.proit.weatherapp.services.LocationService;
import com.proit.weatherapp.types.GetFavouriteLocationInput;
import com.proit.weatherapp.types.GetFavouriteLocationOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class GetFavouriteLocationHelper extends AbstractHelper <GetFavouriteLocationInput, GetFavouriteLocationOutput> {
    private final LocationService locationService;

    @Override
    protected void validateInput(GetFavouriteLocationInput input, Object... args) {

    }

    @Override
    protected void checkPermission(GetFavouriteLocationInput input, Object... args) {

    }

    @Override
    protected GetFavouriteLocationOutput executeHelper(GetFavouriteLocationInput input, Object... args) {
        GetFavouriteLocationOutput output = new GetFavouriteLocationOutput();
        output.setLocations(locationService.getFavoriteLocations());
        return output;
    }
}
