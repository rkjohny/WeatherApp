package com.proit.weatherapp.ui;

import com.vaadin.flow.i18n.I18NProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;


public class TranslationProvider implements I18NProvider {

    private static final Logger logger = LoggerFactory.getLogger(TranslationProvider.class);

    public static final String BUNDLE_PREFIX = "translate";

    public final Locale LOCALE_EN = Locale.of("en", "US");

    private final List<Locale> locales = List.of(Locale.ENGLISH);

    @Override
    public List<Locale> getProvidedLocales() {
        return locales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (key == null) {
            logger.warn("Got lang request for key with null value!");
            return "";
        }

        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);

        String value;
        try {
            value = bundle.getString(key);
        } catch (final MissingResourceException e) {
            logger.warn("Missing resource", e);
            return "!" + locale.getLanguage() + ": " + key;
        }
        if (params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;
    }
}