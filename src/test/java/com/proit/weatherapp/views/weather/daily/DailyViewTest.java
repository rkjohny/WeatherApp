package com.proit.weatherapp.views.weather.daily;

import com.proit.weatherapp.config.Constant;
import com.proit.weatherapp.core.OpenMetroService;
import com.proit.weatherapp.dto.WeatherDataDaily;
import com.proit.weatherapp.dto.CachedData;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.testbench.unit.SpringUIUnitTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DailyViewTest extends SpringUIUnitTest {


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
    public void testDailyView() {
        CachedData cachedData = new CachedData();
        cachedData.setCity("Dhaka");
        cachedData.setLatitude(23.7104);
        cachedData.setLongitude(90.40744);
        cachedData.setTimezone("Asia/Dhaka");
        cachedData.setCountry("Bangladesh");

        VaadinSession.getCurrent().setAttribute(Constant.APP_CACHE_DATA, cachedData);

        DailyWeatherView dailyWeatherView = navigate(DailyWeatherView.class);
        Grid<WeatherDataDaily> dailyWeatherGrid = dailyWeatherView.dailyWeatherGrid;
        var list = ((ListDataProvider<WeatherDataDaily>)dailyWeatherGrid.getDataProvider()).getItems();
        assertEquals(OpenMetroService.MAX_DAILY_FORECAST_RESULT, list.size());
    }
}
