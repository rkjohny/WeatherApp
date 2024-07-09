package com.proit.weatherapp.views.tenperature.hourly;

import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.dto.Location;
import com.proit.weatherapp.dto.TemperatureDaily;
import com.proit.weatherapp.services.TemperatureService;
import com.proit.weatherapp.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;


@PageTitle("Hourly Temperature")
@Route(value = "hourly", layout = MainLayout.class)
@PermitAll
public class TemperatureHourlyView extends VerticalLayout {
    private final I18NProvider i18NProvider;
    private final TemperatureService temperatureService;

    private Location location;
    private TemperatureDaily temperatureDaily;

    public TemperatureHourlyView(I18NProvider i18NProvider, TemperatureService temperatureService) {
        this.i18NProvider = i18NProvider;
        this.temperatureService = temperatureService;
        location = (Location) VaadinSession.getCurrent().getAttribute(Constant.SELECTED_LOCATION_KEY);
        temperatureDaily = (TemperatureDaily) VaadinSession.getCurrent().getAttribute(Constant.SELECTED_DAILY_TEMPERATURE_KEY);

        setSizeFull();
    }
}
