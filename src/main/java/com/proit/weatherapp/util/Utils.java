package com.proit.weatherapp.util;

import com.proit.weatherapp.config.Constant;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;


public final class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

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

    public static byte[] readCountryFlag(String flagFileName) {
        try {
            ClassPathResource resource = new ClassPathResource("country_flag/" + flagFileName);
            Path path = Paths.get(resource.getURI());
            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.warn("Failed to retrieve flag for country. [{}]", e.getMessage());
        }
        return new byte[0];
    }

    public static byte[] readProfilePicture(String profilePictureFileName) {
        try {
            ClassPathResource resource = new ClassPathResource("user_data/" + profilePictureFileName);
            Path path = Paths.get(resource.getURI());
            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.warn("Failed to retrieve profile picture file. [{}]", e.getMessage());
        }
        return new byte[0];
    }

    public static String getCurrentDate(String timezone) {
        ZoneId zoneId = ZoneId.of(timezone);
        // Get the current date and time in the specified timezone
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        // Extract the date part
        LocalDate currentDate = zonedDateTime.toLocalDate();
        // Format the date part as a string in the format "yyyy-MM-dd"
        return currentDate.format(Constant.APP_DATE_FORMATTER);
    }

    public static Locale getLocale() {
        return Locale.getDefault();
    }
}
