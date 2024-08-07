package com.proit.weatherapp.views.location;

import com.proit.weatherapp.api.LocationApi;
import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.dto.CachedData;
import com.proit.weatherapp.dto.Location;
import com.proit.weatherapp.dto.types.*;
import com.proit.weatherapp.util.Utils;
import com.proit.weatherapp.views.MainLayout;
import com.proit.weatherapp.views.weather.daily.DailyWeatherView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.io.ByteArrayInputStream;
import java.util.Comparator;
import java.util.List;


@PageTitle("Location")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class LocationView extends VerticalLayout {
    private static final Logger logger = LoggerFactory.getLogger(LocationView.class);

    private static final int PAGE_SIZE = 10;

    private final LocationApi locationApi;
    private final I18NProvider i18NProvider;

    final Grid<Location> locationGrid = new Grid<>(Location.class);
    private final Span pageInfoSpan = new Span();
    final TextField searchText = new TextField();
    final TextField filterText = new TextField();
    private Button prevButton;
    private Button nextButton;
    private int currentPage = 0;
    private int totalPage = 0;


    public LocationView(LocationApi locationApi, I18NProvider i18NProvider) {
        this.locationApi = locationApi;
        this.i18NProvider = i18NProvider;


        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(getToolbar(), getContent());
        updateGrid();
    }

    private void configureGrid() {
        locationGrid.addClassNames("location-grid");
        locationGrid.setSizeFull();

        locationGrid.setColumns("name", "latitude", "longitude", "elevation", "admin1");
        locationGrid.addComponentColumn(this::addCountryFlag).setHeader("Country").setSortable(true).setComparator(Comparator.comparing(Location::getCountry));
        locationGrid.addComponentColumn(this::addFavoriteButton).setHeader("");
        locationGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        locationGrid.asSingleSelect().addValueChangeListener(valueChangeEvent -> goToDailyTemperatureView(valueChangeEvent.getValue()));
    }

    private Component addCountryFlag(Location location) {
        Avatar avatar = new Avatar(location.getCountryCode());
        String flagFileName = location.getCountryCode().toLowerCase(getLocale()) + ".svg";
        StreamResource streamResource = new StreamResource(flagFileName, () -> new ByteArrayInputStream(Utils.readCountryFlag(flagFileName)));
        avatar.setImageResource(streamResource);
        avatar.setThemeName("xsmall");
        avatar.getElement().setAttribute("tabindex", "-1");

        Div div = new Div();
        div.add(avatar);
        div.add(location.getCountry());
        div.getElement().getStyle().set("display", "flex");
        div.getElement().getStyle().set("align-items", "center");
        div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
        return div;
    }

    private Component addFavoriteButton(Location location) {
        CheckFavouriteInput input = new CheckFavouriteInput();
        input.setLocation(location);
        CheckFavouriteOutput output = locationApi.checkFavorite(input);
        location.setFavorite(output.isFavourite());

        var icon = location.isFavorite() ? LineAwesomeIcon.STAR_SOLID.create() : LineAwesomeIcon.STAR.create();
        return new Button(icon, buttonClickEvent -> toggleFavorite(buttonClickEvent, location));
    }

    private void toggleFavorite(ClickEvent<Button> buttonClickEvent,  Location location) {
        ToggleFavouriteInput input = new ToggleFavouriteInput();
        input.setLocation(location);
        ToggleFavouriteOutput output = locationApi.toggleFavorite(input);
        location.setFavorite(output.isFavourite());

        var icon = location.isFavorite() ? LineAwesomeIcon.STAR_SOLID.create() : LineAwesomeIcon.STAR.create();
        buttonClickEvent.getSource().setIcon(icon);
    }

    private Component getToolbar() {
        String searchByCityText = i18NProvider.getTranslation("search.by.city", Utils.getLocale());
        String filterByNameText = i18NProvider.getTranslation("filter.by.name", Utils.getLocale());

        searchText.setPlaceholder(searchByCityText);
        searchText.setClearButtonVisible(true);
        searchText.setAutofocus(true);
        searchText.setValueChangeMode(ValueChangeMode.LAZY);
        searchText.setSuffixComponent(LineAwesomeIcon.SEARCH_SOLID.create());
        searchText.addKeyPressListener(Key.ENTER, enterEvent -> refreshGrid());

        filterText.setPlaceholder(filterByNameText);
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.setSuffixComponent(LineAwesomeIcon.FILTER_SOLID.create());
        filterText.addValueChangeListener(valueChangeEvent -> refreshGrid());
        //filterText.addKeyPressListener(Key.ENTER, enterEvent -> refreshGrid());

        var toolbar = new HorizontalLayout(searchText, filterText);
        toolbar.addClassName("toolbar");
        toolbar.addClassNames(LumoUtility.Padding.Left.MEDIUM,LumoUtility.Padding.Bottom.NONE);
        return toolbar;
    }

    private VerticalLayout getContent() {
        VerticalLayout content = new VerticalLayout(locationGrid, getPaginationControls());
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private Component getPaginationControls() {
        String prevButtonText = i18NProvider.getTranslation("button.prev", Utils.getLocale());
        String nextButtonText = i18NProvider.getTranslation("button.next", Utils.getLocale());

        prevButton = new Button(prevButtonText, buttonClickEvent -> navigateToPage(currentPage - 1));
        nextButton = new Button(nextButtonText, buttonClickEvent -> navigateToPage(currentPage + 1));
        pageInfoSpan.addClassNames(LumoUtility.Padding.SMALL);

        HorizontalLayout paginationControls = new HorizontalLayout(prevButton, pageInfoSpan, nextButton);
        paginationControls.setJustifyContentMode(JustifyContentMode.CENTER);
        paginationControls.setWidthFull();
        return paginationControls;
    }

    private void goToDailyTemperatureView(Location location) {
        CachedData cachedData = new CachedData();
        cachedData.setLatitude(location.getLatitude());
        cachedData.setLongitude(location.getLongitude());
        cachedData.setTimezone(location.getTimezone());
        cachedData.setCity(location.getName());
        cachedData.setCountry(location.getCountry());
        VaadinSession.getCurrent().setAttribute(Constant.APP_CACHE_DATA, cachedData);
        UI.getCurrent().navigate(DailyWeatherView.class);
    }

    private void navigateToPage(int page) {
        if (page >= 0 && page < totalPage) {
            currentPage = page;
            updateGrid();
        }
    }

    private void updatePageInfoSpan() {
        pageInfoSpan.setVisible(totalPage > 0);
        String pageInfo = i18NProvider.getTranslation("page.current.info", Utils.getLocale(), (currentPage + 1), totalPage);
        pageInfoSpan.setText(pageInfo);
    }

    private void refreshGrid() {
        currentPage = 0;
        totalPage = 0;
        updateGrid();
    }

    private void updateGrid() {
        List<Location> results = getSearchResults(currentPage);
        locationGrid.setItems(results);
        // Update button states
        prevButton.setEnabled(totalPage > 1 && currentPage > 0);
        nextButton.setEnabled(totalPage > 1 && currentPage < totalPage - 1);

        updatePageInfoSpan();
    }

    private List<Location> getSearchResults(int page) {
        List<Location> allResults = getAllSearchResults();
        totalPage = (int) Math.ceil(((double) allResults.size()) / PAGE_SIZE);
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, allResults.size());
        return allResults.subList(start, end);
    }

    private List<Location> getAllSearchResults() {
        GetLocationInput input = new GetLocationInput();
        input.setCity(searchText.getValue());
        input.setFilter(filterText.getValue());
        return locationApi.getLocations(input).getLocations();
    }
}
