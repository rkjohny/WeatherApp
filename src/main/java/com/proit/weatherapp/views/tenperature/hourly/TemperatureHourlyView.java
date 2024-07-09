package com.proit.weatherapp.views.tenperature.hourly;

import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.dto.Location;
import com.proit.weatherapp.dto.TemperatureDaily;
import com.proit.weatherapp.services.TemperatureService;
import com.proit.weatherapp.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
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

        configureGraph();
        add(getToolbar(), getContent());
        updateGraph();
    }

    private void configureGraph() {

    }

    private Component getToolbar() {
        var toolbar = new VerticalLayout();
        toolbar.setPadding(false);
        toolbar.setSpacing(false);
        toolbar.setMargin(false);
        toolbar.addClassNames(LumoUtility.Padding.Left.MEDIUM);

        Span pageHeader = new Span(i18NProvider.getTranslation("hourly.temperature.page.header", getLocale(), location.getName()));
        pageHeader.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD);
        toolbar.add(pageHeader);
        toolbar.add(new Span(i18NProvider.getTranslation("hourly.temperature.page.header.date", getLocale(), temperatureDaily.getDate())));
        return toolbar;
    }

    private VerticalLayout getContent() {
        VerticalLayout content = new VerticalLayout();
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void updateGraph() {

    }
}
