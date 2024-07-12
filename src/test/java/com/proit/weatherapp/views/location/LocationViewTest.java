package com.proit.weatherapp.views.location;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.proit.weatherapp.dto.Location;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.testbench.unit.SpringUIUnitTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;


@SpringBootTest
public class LocationViewTest extends SpringUIUnitTest {

    static {
        System.setProperty("vaadin.launch-browser", "false");
    }

    @AfterEach
    void logout() {
        if (VaadinSession.getCurrent() != null) {
            if (VaadinSession.getCurrent().getSession() != null) {
                VaadinSession.getCurrent().getSession().invalidate();
            }
        }
    }

    @Test
    @WithMockUser
    public void searchLocationTest() {
        LocationView locationView = navigate(LocationView.class);

        Grid<Location> grid = locationView.locationGrid;
        TextField searchText = locationView.searchText;
        TextField filterText = locationView.filterText;

        searchText.setValue("London");
        filterText.setValue("London");

        Location firstLocation = getFirstItem(grid);

        //grid.asSingleSelect().setValue(firstLocation);
        assertEquals("London", firstLocation.getName ());
    }

    private Location getFirstItem(Grid<Location> grid) {
        return ((ListDataProvider<Location>) grid.getDataProvider()).getItems().iterator().next();
    }
}
