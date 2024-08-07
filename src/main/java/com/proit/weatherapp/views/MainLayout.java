package com.proit.weatherapp.views;

import com.proit.weatherapp.entity.User;
import com.proit.weatherapp.security.AuthenticatedUser;
import com.proit.weatherapp.util.Utils;
import com.proit.weatherapp.views.favourite.FavouriteView;
import com.proit.weatherapp.views.location.LocationView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H1 viewTitle;

    private final I18NProvider i18NProvider;
    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;

    public MainLayout(I18NProvider i18NProvider, AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.i18NProvider = i18NProvider;
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span(i18NProvider.getTranslation("app.name", Utils.getLocale()));
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        if (accessChecker.hasAccess(LocationView.class)) {
            nav.addItem(new SideNavItem(i18NProvider.getTranslation("location.search", Utils.getLocale()), LocationView.class, LineAwesomeIcon.LOCATION_ARROW_SOLID.create()));
        }
        if (accessChecker.hasAccess(FavouriteView.class)) {
            nav.addItem(new SideNavItem(i18NProvider.getTranslation("location.favourite", Utils.getLocale()), FavouriteView.class, LineAwesomeIcon.STAR_SOLID.create()));
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());
            StreamResource streamResource = new StreamResource("profile-pic", () -> new ByteArrayInputStream(user.getProfilePicture()));

            avatar.setImageResource(streamResource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div signOutDiv = new Div();
            signOutDiv.add(avatar);
            signOutDiv.add(user.getName());
            signOutDiv.add(new Icon("lumo", "dropdown"));
            signOutDiv.getElement().getStyle().set("display", "flex");
            signOutDiv.getElement().getStyle().set("align-items", "center");
            signOutDiv.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(signOutDiv);
            userName.getSubMenu().addItem(i18NProvider.getTranslation("sign.out", Utils.getLocale()), menuItemClickEvent -> authenticatedUser.logout());

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(Utils.getCurrentPageTitle(getContent()));
    }

}
