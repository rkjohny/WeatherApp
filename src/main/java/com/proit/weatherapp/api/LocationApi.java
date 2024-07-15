package com.proit.weatherapp.api;


import com.proit.weatherapp.core.CheckFavouriteHelper;
import com.proit.weatherapp.core.GetFavouriteLocationHelper;
import com.proit.weatherapp.core.GetLocationHelper;
import com.proit.weatherapp.core.ToggleFavouriteHelper;
import com.proit.weatherapp.dto.Location;
import com.proit.weatherapp.services.LocationService;
import com.proit.weatherapp.types.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class LocationApi {

    private final ObjectProvider<GetLocationHelper> getLocationHelperProvider;
    private final ObjectProvider<GetFavouriteLocationHelper> getFavouriteLocationHelperProvider;
    private final ObjectProvider<CheckFavouriteHelper> checkFavouriteHelperProvider;
    private final ObjectProvider<ToggleFavouriteHelper> toggleFavouriteHelperProvider;

    private final LocationService locationService;



    /**
     * Get all the location by a city filtered by a filter text.
     *
     * @param input the {@link GetLocationOutput} object that holds the name of the location and the filter text
     * @return {@link GetLocationOutput} object that holds the list of the location by the name(city)
     */
    public GetLocationOutput getLocations(GetLocationInput input) {
        GetLocationHelper helper = getLocationHelperProvider.getObject();
        return helper.execute(input);
    }

    /**
     * Check if a location has been marked as favorite by the current logged-in user
     *
     * @param input the {@link CheckFavouriteInput} object that hold the location's id to check
     * @return @{@link CheckFavouriteOutput} the object that holds a boolean member favourite which indicates
     * whether the location has been marked as favorite
     */
    public CheckFavouriteOutput checkFavorite(CheckFavouriteInput input) {
        CheckFavouriteHelper helper = checkFavouriteHelperProvider.getObject();
        return helper.execute(input);
    }

    /**
     * Toggle the favorite mark of a location by the current logged-in user
     *
     * @param input {@link ToggleFavouriteInput} object that holds the {@link Location} object to toggle
     * @return {@link ToggleFavouriteOutput} object that holds a member favourite which will indicate
     * if the location is marked as favourite after toggling
     */
    public ToggleFavouriteOutput toggleFavorite(ToggleFavouriteInput input) {
        ToggleFavouriteHelper helper = toggleFavouriteHelperProvider.getObject();
        return helper.execute(input);
    }


    /**
     * Get all the locations marked as favorite by the current logged-in user
     *
     * @param input the {@link GetFavouriteLocationInput} object
     * @return {@link GetFavouriteLocationOutput} object that holds the list of the locations marked as favorite
     */
    public GetFavouriteLocationOutput getFavoriteLocations(GetFavouriteLocationInput input) {
        GetFavouriteLocationHelper helper = getFavouriteLocationHelperProvider.getObject();
        return helper.execute(input);
    }
}
