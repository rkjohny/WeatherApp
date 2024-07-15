package com.proit.weatherapp.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public final class Sanitizer {
    private Sanitizer() {
    }

    public static String sanitize(String s) {
        return Jsoup.clean(s, Safelist.basic());
    }
}
