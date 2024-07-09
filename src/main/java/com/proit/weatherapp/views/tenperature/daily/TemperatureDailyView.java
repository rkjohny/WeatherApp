package com.proit.weatherapp.views.tenperature.daily;

import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.dto.Location;
import com.proit.weatherapp.dto.TemperatureDaily;
import com.proit.weatherapp.services.TemperatureService;
import com.proit.weatherapp.views.MainLayout;
import com.proit.weatherapp.views.tenperature.hourly.TemperatureHourlyView;
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


@PageTitle("Daily Temperature")
@Route(value = "daily", layout = MainLayout.class)
@PermitAll
public class TemperatureDailyView extends VerticalLayout {

    private final I18NProvider i18NProvider;
    private final TemperatureService temperatureService;

    private Location location;
    private final Grid<TemperatureDaily> temperatureGrid = new Grid<>(TemperatureDaily.class);

    public TemperatureDailyView(I18NProvider i18NProvider, TemperatureService temperatureService) {
        this.i18NProvider = i18NProvider;
        this.temperatureService = temperatureService;
        location = (Location) VaadinSession.getCurrent().getAttribute(Constant.SELECTED_LOCATION_KEY);

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(), getContent());
        updateGrid();
    }

    private void configureGrid() {
        temperatureGrid.addClassNames("location-grid");
        temperatureGrid.setSizeFull();

        temperatureGrid.setColumns("date", "maxTemperature", "minTemperature", "precipitation", "probability", "maxWind");
        temperatureGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        temperatureGrid.asSingleSelect().addValueChangeListener(valueChangeEvent -> goToHourlyTemperatureView(valueChangeEvent.getValue()));
    }

    private Component getToolbar() {
        var toolbar = new VerticalLayout();
        toolbar.setPadding(false);
        toolbar.setSpacing(false);
        toolbar.setMargin(false);
        toolbar.addClassNames(LumoUtility.Padding.Left.MEDIUM);

        Span pageHeader = new Span(i18NProvider.getTranslation("daily.temperature.page.header", getLocale(), location.getName()));
        pageHeader.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD);
        toolbar.add(pageHeader);
        toolbar.add(new Span(i18NProvider.getTranslation("daily.temperature.page.header.unit", getLocale())));

        var layoutUnits = new VerticalLayout();
        layoutUnits.setPadding(false);
        layoutUnits.setSpacing(false);
        layoutUnits.setMargin(false);
        layoutUnits.addClassName(LumoUtility.Padding.Left.LARGE);

        layoutUnits.add(new Span(i18NProvider.getTranslation("daily.temperature.page.header.unit.temperature", getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("daily.temperature.page.header.unit.wind", getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("daily.temperature.page.header.unit.precipitation", getLocale())));
        layoutUnits.add(new Span(i18NProvider.getTranslation("daily.temperature.page.header.unit.probability", getLocale())));
        toolbar.add(layoutUnits);

        return toolbar;
    }

    private VerticalLayout getContent() {
        VerticalLayout content = new VerticalLayout(temperatureGrid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void goToHourlyTemperatureView(TemperatureDaily temperatureDaily) {
        VaadinSession.getCurrent().setAttribute(Constant.SELECTED_DAILY_TEMPERATURE_KEY, temperatureDaily);
        UI.getCurrent().navigate(TemperatureHourlyView.class);
    }

    private void updateGrid() {
        List<TemperatureDaily> results = getDailyTemperatureForecast();
        temperatureGrid.setItems(results);
    }

    private List<TemperatureDaily> getDailyTemperatureForecast() {
        return temperatureService.getDailyTemperature(location.getLatitude(), location.getLongitude(), location.getTimezone());
    }
}
