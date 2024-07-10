package com.proit.weatherapp.views.weather.hourly;

import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.dto.WeatherDataHourly;
import com.proit.weatherapp.services.WeatherService;
import com.proit.weatherapp.types.CachedData;
import com.proit.weatherapp.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.util.List;


@PageTitle("Hourly Weather")
@Route(value = "hourly", layout = MainLayout.class)
@PermitAll
public class HourlyWeatherView extends VerticalLayout {
    private final I18NProvider i18NProvider;
    private final WeatherService weatherService;

    private final CachedData cachedData;

    private final Chart hourlyChart;
    private final Configuration hourlyChartConf;


    public HourlyWeatherView(I18NProvider i18NProvider, WeatherService weatherService) {
        this.i18NProvider = i18NProvider;
        this.weatherService = weatherService;
        cachedData = (CachedData) VaadinSession.getCurrent().getAttribute(Constant.APP_CACHE_DATA);

        hourlyChart = new Chart(ChartType.LINE);
        hourlyChartConf = hourlyChart.getConfiguration();

        setSizeFull();
        configureGraph();
        add(getToolbar(), getContent());
        updateGraph();
    }

    private void configureGraph() {
        hourlyChartConf.setTitle(i18NProvider.getTranslation("hourly.weather.page.graph.title", getLocale()));

        // Configure the X-axis to treat data as datetime
        XAxis xAxis = new XAxis();
        xAxis.setType(AxisType.DATETIME);
        xAxis.setTitle(i18NProvider.getTranslation("hourly.weather.page.graph.xAxis.title", getLocale()));
        hourlyChartConf.addxAxis(xAxis);

        // Configure the Y-axis
        YAxis yAxis = new YAxis();
        yAxis.setTitle(i18NProvider.getTranslation("hourly.weather.page.graph.yAxis.title", getLocale()));
        yAxis.setAllowDecimals(true);
        yAxis.setTickInterval(5);
        hourlyChartConf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setEnabled(true);
        //tooltip.setFormatter("function() { return this.y; }");
        tooltip.setPointFormat("{point.y}");
        hourlyChartConf.setTooltip(tooltip);
    }

    private Component getToolbar() {
        var toolbar = new VerticalLayout();
        toolbar.setPadding(false);
        toolbar.setSpacing(false);
        toolbar.setMargin(false);
        toolbar.addClassNames(LumoUtility.Padding.Left.MEDIUM);

        Span pageHeader = new Span(i18NProvider.getTranslation("hourly.weather.page.header", getLocale(), cachedData.getCity(), cachedData.getCountry()));
        pageHeader.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD);
        toolbar.add(pageHeader);
        toolbar.add(new Span(i18NProvider.getTranslation("hourly.weather.page.header.date", getLocale(), cachedData.getDate())));
        return toolbar;
    }

    private VerticalLayout getContent() {
        VerticalLayout content = new VerticalLayout();
        content.addClassNames("content");
        content.setSizeFull();
        content.add(hourlyChart);
        return content;
    }

    private void updateGraph() {
        var hourlyData = getAPIResults();
        hourlyChartConf.addSeries(getTemperatureData(hourlyData));
        hourlyChartConf.addSeries(getPrecipitationData(hourlyData));
        hourlyChartConf.addSeries(getWindData(hourlyData));
    }

    private DataSeries getTemperatureData(List<WeatherDataHourly> hourlyData) {
        DataSeries series = new DataSeries();
        series.setName(i18NProvider.getTranslation("hourly.weather.page.graph.line.temperature.name", getLocale()));

        hourlyData.forEach(data -> series.add(new DataSeriesItem(WeatherDataHourly.toEpochMilli(data), data.getTemperature())));
        return series;
    }

    private DataSeries getPrecipitationData(List<WeatherDataHourly> hourlyData) {
        DataSeries series = new DataSeries();
        series.setName(i18NProvider.getTranslation("hourly.weather.page.graph.line.precipitation.name", getLocale()));

        hourlyData.forEach(data -> series.add(new DataSeriesItem(WeatherDataHourly.toEpochMilli(data), data.getPrecipitation())));
        return series;
    }

    private DataSeries getWindData(List<WeatherDataHourly> hourlyData) {
        DataSeries series = new DataSeries();
        series.setName(i18NProvider.getTranslation("hourly.weather.page.graph.line.wind.name", getLocale()));

        hourlyData.forEach(data -> series.add(new DataSeriesItem(WeatherDataHourly.toEpochMilli(data), data.getWind())));
        return series;
    }

    private List<WeatherDataHourly> getAPIResults() {
        return weatherService.getHourlyWeather(cachedData.getLatitude(), cachedData.getLongitude(), cachedData.getTimezone(), cachedData.getDate());
    }
}
