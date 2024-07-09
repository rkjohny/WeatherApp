package com.proit.weatherapp.views.location;

import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.dto.Location;
import com.proit.weatherapp.services.LocationService;
import com.proit.weatherapp.views.MainLayout;
import com.proit.weatherapp.views.tenperature.daily.TemperatureDailyView;
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
import org.springframework.core.io.ClassPathResource;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;


@PageTitle("Location")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class LocationView extends VerticalLayout {
    private static final Logger logger = LoggerFactory.getLogger(LocationView.class);

    private final LocationService locationService;
    private final I18NProvider i18NProvider;

    private final Grid<Location> locationGrid = new Grid<>(Location.class);

    private final Span pageInfoSpan = new Span();
    private final TextField searchText = new TextField();
    private final TextField filterText = new TextField();
    private Button prevButton;
    private Button nextButton;
    private int currentPage = 0;
    private int totalPage = 0;
    private static final int PAGE_SIZE = 10;


    public LocationView(LocationService locationService, I18NProvider i18NProvider) {
        this.locationService = locationService;
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
        locationGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        locationGrid.asSingleSelect().addValueChangeListener(valueChangeEvent -> goToDailyTemperatureView(valueChangeEvent.getValue()));
    }

    private Component addCountryFlag(Location location) {
        Avatar avatar = new Avatar(location.getCountryCode());
        StreamResource streamResource = null;
        try {
            String flagFileName = location.getCountryCode().toLowerCase(getLocale()) + ".svg";
            String flagFile = "country_flag/" + flagFileName;
            ClassPathResource resource = new ClassPathResource(flagFile);
            Path path = Paths.get(resource.getURI());
            byte[] flagData = Files.readAllBytes(path);
            streamResource = new StreamResource(flagFileName, () -> new ByteArrayInputStream(flagData));
        } catch (IOException e) {
            logger.warn("Failed to retrieve flag for country. [{}]", e.getMessage());
        }

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

    private Component getToolbar() {
        String searchByCityText = i18NProvider.getTranslation("search.by.city", getLocale());
        String filterByNameText = i18NProvider.getTranslation("filter.by.name", getLocale());

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
        filterText.addKeyPressListener(Key.ENTER, enterEvent -> refreshGrid());

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
        String prevButtonText = i18NProvider.getTranslation("button.prev", getLocale());
        String nextButtonText = i18NProvider.getTranslation("button.next", getLocale());

        prevButton = new Button(prevButtonText, buttonClickEvent -> navigateToPage(currentPage - 1));
        nextButton = new Button(nextButtonText, buttonClickEvent -> navigateToPage(currentPage + 1));
        pageInfoSpan.addClassNames(LumoUtility.Padding.SMALL);

        HorizontalLayout paginationControls = new HorizontalLayout(prevButton, pageInfoSpan, nextButton);
        paginationControls.setJustifyContentMode(JustifyContentMode.CENTER);
        paginationControls.setWidthFull();
        return paginationControls;
    }

    private void goToDailyTemperatureView(Location location) {
        VaadinSession.getCurrent().setAttribute(Constant.SELECTED_LOCATION_KEY, location);
        UI.getCurrent().navigate(TemperatureDailyView.class);
    }

    private void navigateToPage(int page) {
        if (page >= 0 && page < totalPage) {
            currentPage = page;
            updateGrid();
        }
    }

    private void updatePageInfoSpan() {
        pageInfoSpan.setVisible(totalPage > 0);
        String pageInfo = i18NProvider.getTranslation("page.current.info", getLocale(), (currentPage + 1), totalPage);
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
        return locationService.getLocations(searchText.getValue(), filterText.getValue());
    }
}
