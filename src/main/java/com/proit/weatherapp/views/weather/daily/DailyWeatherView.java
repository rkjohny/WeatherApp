package com.proit.weatherapp.views.weather.daily;

import com.proit.weatherapp.api.WeatherApi;
import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.dto.WeatherDataDaily;
import com.proit.weatherapp.dto.CachedData;
import com.proit.weatherapp.dto.types.GetDailyWeatherInput;
import com.proit.weatherapp.util.Utils;
import com.proit.weatherapp.views.MainLayout;
import com.proit.weatherapp.views.weather.hourly.HourlyWeatherView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.util.List;


@PageTitle("Daily Weather")
@Route(value = "daily", layout = MainLayout.class)
@PermitAll
public class DailyWeatherView extends VerticalLayout {

    private final I18NProvider i18NProvider;
    private final WeatherApi weatherApi;

    private CachedData cachedData;
    final Grid<WeatherDataDaily> dailyWeatherGrid = new Grid<>(WeatherDataDaily.class);

    public DailyWeatherView(I18NProvider i18NProvider, WeatherApi weatherApi) {
        this.i18NProvider = i18NProvider;
        this.weatherApi = weatherApi;
        cachedData = (CachedData) VaadinSession.getCurrent().getAttribute(Constant.APP_CACHE_DATA);

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(), getContent());
        updateGrid();
    }

    private void configureGrid() {
        dailyWeatherGrid.addClassNames("location-grid");
        dailyWeatherGrid.setSizeFull();

        dailyWeatherGrid.setColumns("date", "maxTemperature", "minTemperature", "precipitation", "probability", "maxWind");
        dailyWeatherGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        dailyWeatherGrid.asSingleSelect().addValueChangeListener(valueChangeEvent -> goToHourlyTemperatureView(valueChangeEvent.getValue()));
    }

    private Component getToolbar() {
        var toolbar = new VerticalLayout();
        toolbar.setPadding(false);
        toolbar.setSpacing(false);
        toolbar.setMargin(false);
        toolbar.addClassNames(LumoUtility.Padding.Left.MEDIUM);

        Span pageHeader = new Span(i18NProvider.getTranslation("daily.weather.page.header", Utils.getLocale(), cachedData.getCity(), cachedData.getCountry()));
        pageHeader.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD);
        toolbar.add(pageHeader);
        toolbar.add(new Span(i18NProvider.getTranslation("daily.weather.page.header.unit", getLocale())));

        var layoutUnits = new VerticalLayout();
        layoutUnits.setPadding(false);
        layoutUnits.setSpacing(false);
        layoutUnits.setMargin(false);
        layoutUnits.addClassName(LumoUtility.Padding.Left.LARGE);

        layoutUnits.add(new Span(i18NProvider.getTranslation("daily.weather.page.header.unit.temperature", Utils.getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("daily.weather.page.header.unit.wind", Utils.getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("daily.weather.page.header.unit.precipitation", Utils.getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("daily.weather.page.header.unit.probability", Utils.getLocale())));
        toolbar.add(layoutUnits);

        return toolbar;
    }

    private VerticalLayout getContent() {
        VerticalLayout content = new VerticalLayout(dailyWeatherGrid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void goToHourlyTemperatureView(WeatherDataDaily weatherDataDaily) {
        cachedData.setDate(weatherDataDaily.getDate());
        VaadinSession.getCurrent().setAttribute(Constant.APP_CACHE_DATA, cachedData);
        UI.getCurrent().navigate(HourlyWeatherView.class);
    }

    private void updateGrid() {
        List<WeatherDataDaily> results = getDailyTemperatureForecast();
        dailyWeatherGrid.setItems(results);
    }

    private List<WeatherDataDaily> getDailyTemperatureForecast() {
        GetDailyWeatherInput input = new GetDailyWeatherInput();
        input.setLatitude(cachedData.getLatitude());
        input.setLongitude(cachedData.getLongitude());
        input.setTimezone(cachedData.getTimezone());
        return weatherApi.getDailyWeather(input).getDataDailies();
    }
}
