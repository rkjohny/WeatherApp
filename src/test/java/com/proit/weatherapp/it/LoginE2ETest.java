package com.proit.weatherapp.it;

import com.proit.weatherapp.it.elements.LoginViewElement;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.testbench.BrowserTest;
import com.vaadin.testbench.BrowserTestBase;
import com.vaadin.testbench.IPAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginE2ETest extends BrowserTestBase {

    @Autowired
    Environment environment;


    @BeforeEach
    void openBrowser() {
        getDriver().get("http://" + IPAddress.findSiteLocalAddress() + ":" + environment.getProperty("local.server.port") + "/");
    }

    void logout() {
        if (VaadinSession.getCurrent() != null) {
            if (VaadinSession.getCurrent().getSession() != null) {
                VaadinSession.getCurrent().getSession().invalidate();
            }
        }
    }

//     TODO: logout is not working properly, so subsequent test cases behave unexpectedly, after fixing logout issue, the following
//     test cases can be un commented

//    @BrowserTest
//    public void loginAsValidUserSucceeds() {
//        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
//        Assertions.assertTrue(loginView.login("david", "password"));
//        logout();
//    }

//    @BrowserTest
//    public void loginAsInvalidUserFails() {
//        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
//        Assertions.assertFalse(loginView.login("user", "invalid"));
//    }

}
