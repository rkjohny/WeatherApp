package com.proit.weatherapp.util;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import org.apache.commons.lang3.StringUtils;


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

    public static Double checkNull(Double val) {
        return val == null ? 0D : val;
    }

    public static Integer checkNull(Integer val) {
        return val == null ? 0 : val;
    }

    public static String checkNull(String val) {
        return StringUtils.isBlank(val) ? StringUtils.EMPTY : val;
    }
}
