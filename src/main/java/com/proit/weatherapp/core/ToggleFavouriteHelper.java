package com.proit.weatherapp.core;

import com.proit.weatherapp.services.LocationService;
import com.proit.weatherapp.dto.types.ToggleFavouriteInput;
import com.proit.weatherapp.dto.types.ToggleFavouriteOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class ToggleFavouriteHelper extends AbstractHelper <ToggleFavouriteInput, ToggleFavouriteOutput> {
    private final LocationService locationService;

    @Override
    protected void validateInput(ToggleFavouriteInput input, Object... args) {

    }

    @Override
    protected void checkPermission(ToggleFavouriteInput input, Object... args) {

    }

    @Override
    protected ToggleFavouriteOutput executeHelper(ToggleFavouriteInput input, Object... args) {
        ToggleFavouriteOutput output = new ToggleFavouriteOutput();
        output.setFavourite(locationService.toggleFavorite(input.getLocation()));
        return output;
    }
}
