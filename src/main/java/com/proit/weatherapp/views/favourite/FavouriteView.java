package com.proit.weatherapp.views.favourite;

import com.proit.weatherapp.api.WeatherApi;
import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.dto.WeatherDataCurrent;
import com.proit.weatherapp.services.LocationService;
import com.proit.weatherapp.dto.CachedData;
import com.proit.weatherapp.dto.types.GetCurrentWeatherInput;
import com.proit.weatherapp.util.Utils;
import com.proit.weatherapp.views.MainLayout;
import com.proit.weatherapp.views.weather.hourly.HourlyWeatherView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.util.List;


@PageTitle("Favourite")
@Route(value = "favourite", layout = MainLayout.class)
@PermitAll
public class FavouriteView extends VerticalLayout {

    private final I18NProvider i18NProvider;
    private final LocationService locationService;
    private final WeatherApi weatherApi;

    private final Grid<WeatherDataCurrent> weatherDataCurrentGrid = new Grid<>(WeatherDataCurrent.class);

    public FavouriteView(I18NProvider i18NProvider, LocationService locationService, WeatherApi weatherApi) {
        this.i18NProvider = i18NProvider;
        this.locationService = locationService;
        this.weatherApi = weatherApi;

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(), getContent());
        updateGrid();
    }

    private void configureGrid() {
        weatherDataCurrentGrid.addClassNames("location-grid");
        weatherDataCurrentGrid.setSizeFull();

        weatherDataCurrentGrid.setColumns("city", "country", "temperature", "precipitation", "wind", "windDirection", "cloudLow", "weatherCode");
        weatherDataCurrentGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        weatherDataCurrentGrid.asSingleSelect().addValueChangeListener(valueChangeEvent -> goToDailyTemperatureView(valueChangeEvent.getValue()));
    }

    private Component getToolbar() {
        var toolbar = new VerticalLayout();
        toolbar.setPadding(false);
        toolbar.setSpacing(false);
        toolbar.setMargin(false);
        toolbar.addClassNames(LumoUtility.Padding.Left.MEDIUM);

        Span pageHeader = new Span(i18NProvider.getTranslation("favorite.weather.page.header", Utils.getLocale()));
        pageHeader.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD);
        toolbar.add(pageHeader);
        toolbar.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit", Utils.getLocale())));

        var layoutUnits = new VerticalLayout();
        layoutUnits.setPadding(false);
        layoutUnits.setSpacing(false);
        layoutUnits.setMargin(false);
        layoutUnits.addClassName(LumoUtility.Padding.Left.LARGE);

        layoutUnits.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit.temperature", Utils.getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit.wind", Utils.getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit.precipitation", Utils.getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit.cloud", Utils.getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit.weatherCode", Utils.getLocale())));
        toolbar.add(layoutUnits);

        return toolbar;
    }

    private VerticalLayout getContent() {
        VerticalLayout content = new VerticalLayout(weatherDataCurrentGrid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void goToDailyTemperatureView(WeatherDataCurrent dataCurrent) {
        CachedData cachedData = new CachedData();
        cachedData.setLatitude(dataCurrent.getLatitude());
        cachedData.setLongitude(dataCurrent.getLongitude());
        cachedData.setTimezone(dataCurrent.getTimezone());
        cachedData.setCity(dataCurrent.getCity());
        cachedData.setCountry(dataCurrent.getCountry());
        cachedData.setDate(Utils.getCurrentDate(dataCurrent.getTimezone()));
        VaadinSession.getCurrent().setAttribute(Constant.APP_CACHE_DATA, cachedData);
        UI.getCurrent().navigate(HourlyWeatherView.class);
    }

    private void updateGrid() {
        List<WeatherDataCurrent> results = getCurrentTemperatureForecast();
        weatherDataCurrentGrid.setItems(results);
    }

    private List<WeatherDataCurrent> getCurrentTemperatureForecast() {
        var favLocations = locationService.getFavoriteLocations();
        GetCurrentWeatherInput input = new GetCurrentWeatherInput();
        input.setLocations(favLocations);
        return weatherApi.getCurrentWeather(input).getDataCurrents();
    }
}
