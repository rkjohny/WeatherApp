package com.proit.weatherapp.views.login;

import com.proit.weatherapp.security.AuthenticatedUser;
import com.proit.weatherapp.util.Utils;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    private final I18NProvider i18NProvider;
    private final AuthenticatedUser authenticatedUser;


    public LoginView(I18NProvider i18NProvider, AuthenticatedUser authenticatedUser) {
        this.i18NProvider = i18NProvider;
        this.authenticatedUser = authenticatedUser;

        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        String appName = i18NProvider.getTranslation("app.name", Utils.getLocale());

        Image logoImage = new Image("images/app_logo.png", "app logo");
        logoImage.setWidth("100px");
        logoImage.setHeight("100px");
        Span appnNameSpan = new Span(appName);
        appnNameSpan.addClassNames(LumoUtility.FontSize.XXLARGE);

        var title = new VerticalLayout(logoImage, appnNameSpan);
        title.setAlignItems(FlexComponent.Alignment.CENTER);
        title.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        //i18n.getHeader().setTitle(appName);
        i18n.setAdditionalInformation(null);

        setTitle(title);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);
        setOpened(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            // Already logged in
            setOpened(false);
            event.forwardTo("");
        }
        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}
