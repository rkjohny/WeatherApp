package com.proit.weatherapp.it.elements;

import com.vaadin.flow.component.login.testbench.LoginFormElement;
import com.vaadin.flow.component.orderedlayout.testbench.VerticalLayoutElement;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


public class LoginViewElement extends VerticalLayoutElement {

    public boolean login(String username, String password) {
        LoginFormElement form = $(LoginFormElement.class).first();
        form.getUsernameField().setValue(username);
        form.getPasswordField().setValue(password);
        form.getSubmitButton().click();

        // Return true if we end up on Location page
        try {
            getDriver().manage().timeouts().implicitlyWait(Duration.of(5, ChronoUnit.SECONDS));
            return "Location".compareTo(getDriver().getTitle()) == 0;
        } catch (Exception e) {
            return false;
        }
    }

}