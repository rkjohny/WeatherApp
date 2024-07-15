package com.proit.weatherapp.core;

import com.proit.weatherapp.services.LocationService;
import com.proit.weatherapp.types.CheckFavouriteInput;
import com.proit.weatherapp.types.CheckFavouriteOutput;
import com.proit.weatherapp.util.InputValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class CheckFavouriteHelper extends AbstractHelper <CheckFavouriteInput, CheckFavouriteOutput> {
    private final LocationService locationService;

    @Override
    protected void validateInput(CheckFavouriteInput input, Object... args) {
        InputValidationUtils.validateObject(input.getLocation());
    }

    @Override
    protected void checkPermission(CheckFavouriteInput input, Object... args) {

    }

    @Override
    protected CheckFavouriteOutput executeHelper(CheckFavouriteInput input, Object... args) {
        CheckFavouriteOutput output = new CheckFavouriteOutput();
        output.setFavourite(locationService.checkFavorite(input.getLocation()));
        return output;
    }
}
