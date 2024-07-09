package com.proit.weatherapp.util;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;


public final class Utils {

    private Utils() {
    }

    public static String getCurrentPageTitle(Component component) {
        if (component == null) {
            return "";
        } else if (component instanceof HasDynamicTitle titleHolder) {
            return titleHolder.getPageTitle();
        } else {
            var annotation = component.getClass().getAnnotation(PageTitle.class);
            if (annotation != null) {
                return annotation.value();
            }
        }
        return "";
    }

}
