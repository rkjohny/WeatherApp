package com.proit.weatherapp.security;

import com.proit.weatherapp.views.login.LoginView;
import com.proit.weatherapp.views.weather.daily.DailyWeatherView;
import com.vaadin.flow.router.RouteNotFoundError;
import com.vaadin.testbench.unit.SpringUIUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

@SpringBootTest
public class ViewSecurityTest extends SpringUIUnitTest {

    static {
        System.setProperty("vaadin.launch-browser", "false");
    }

    @Test
    @WithAnonymousUser
    void loginViewAccessible() {
        // login view should be accessible
        Assertions.assertInstanceOf(LoginView.class, getCurrentView());
    }

    @Test
    @WithAnonymousUser
    void locationViewNotAccessible() {
        // location view should NOT be accessible
        RouteNotFoundError errorView = navigate("location", RouteNotFoundError.class);
        Assertions.assertTrue(errorView.getElement().getChild(0).getOuterHTML().contains("Could not navigate to 'location'"));
    }

    @Test
    @WithAnonymousUser
    void dailyViewNotAccessible() {
        // daily view should NOT be accessible, should be routed to login
        Assertions.assertThrows(IllegalArgumentException.class, () -> navigate(DailyWeatherView.class));
    }
}
