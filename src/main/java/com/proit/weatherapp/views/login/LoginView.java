package com.proit.weatherapp.views.login;

import com.proit.weatherapp.security.AuthenticatedUser;
import com.proit.weatherapp.util.Utils;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login | Vaadin CRM")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final I18NProvider i18NProvider;
    private final AuthenticatedUser authenticatedUser;

    private final LoginForm login = new LoginForm();

    public LoginView(I18NProvider i18NProvider, AuthenticatedUser authenticatedUser) {
        this.i18NProvider = i18NProvider;
        this.authenticatedUser = authenticatedUser;

        //addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);

        String appName = i18NProvider.getTranslation("app.name", Utils.getLocale());
        add(new H1(appName));
        add(login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		if (authenticatedUser.get().isPresent()) {
			// Already logged in
			setVisible(false);
			beforeEnterEvent.forwardTo("");
		}

        // inform the user about an authentication error
		login.setError(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}