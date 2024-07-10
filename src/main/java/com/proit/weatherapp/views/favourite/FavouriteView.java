package com.proit.weatherapp.views.favourite;

import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.dto.WeatherDataCurrent;
import com.proit.weatherapp.services.LocationService;
import com.proit.weatherapp.services.WeatherService;
import com.proit.weatherapp.views.MainLayout;
import com.proit.weatherapp.views.weather.daily.DailyWeatherView;
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
    private final WeatherService weatherService;

    private final Grid<WeatherDataCurrent> temperatureGrid = new Grid<>(WeatherDataCurrent.class);

    public FavouriteView(I18NProvider i18NProvider, LocationService locationService, WeatherService weatherService) {
        this.i18NProvider = i18NProvider;
        this.locationService = locationService;
        this.weatherService = weatherService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(), getContent());
        updateGrid();
    }

    private void configureGrid() {
        temperatureGrid.addClassNames("location-grid");
        temperatureGrid.setSizeFull();

        temperatureGrid.setColumns("city", "country", "temperature", "precipitation", "wind", "windDirection", "cloudLow", "weatherCode");
        temperatureGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        temperatureGrid.asSingleSelect().addValueChangeListener(valueChangeEvent -> goToDailyTemperatureView(valueChangeEvent.getValue()));
    }

    private Component getToolbar() {
        var toolbar = new VerticalLayout();
        toolbar.setPadding(false);
        toolbar.setSpacing(false);
        toolbar.setMargin(false);
        toolbar.addClassNames(LumoUtility.Padding.Left.MEDIUM);

        Span pageHeader = new Span(i18NProvider.getTranslation("favorite.weather.page.header", getLocale()));
        pageHeader.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD);
        toolbar.add(pageHeader);
        toolbar.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit", getLocale())));

        var layoutUnits = new VerticalLayout();
        layoutUnits.setPadding(false);
        layoutUnits.setSpacing(false);
        layoutUnits.setMargin(false);
        layoutUnits.addClassName(LumoUtility.Padding.Left.LARGE);

        layoutUnits.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit.temperature", getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit.wind", getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit.precipitation", getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit.cloud", getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("favorite.weather.page.header.unit.weatherCode", getLocale())));
        toolbar.add(layoutUnits);

        return toolbar;
    }

    private VerticalLayout getContent() {
        VerticalLayout content = new VerticalLayout(temperatureGrid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void goToDailyTemperatureView(WeatherDataCurrent weatherDataCurrent) {
        VaadinSession.getCurrent().setAttribute(Constant.SELECTED_CURRENT_WEATHER_KEY, weatherDataCurrent);
        UI.getCurrent().navigate(DailyWeatherView.class);
    }

    private void updateGrid() {
        List<WeatherDataCurrent> results = getCurrentTemperatureForecast();
        temperatureGrid.setItems(results);
    }

    private List<WeatherDataCurrent> getCurrentTemperatureForecast() {
        var favLocations = locationService.getFavoriteLocations();
        return weatherService.getCurrentWeather(favLocations);
    }
}
