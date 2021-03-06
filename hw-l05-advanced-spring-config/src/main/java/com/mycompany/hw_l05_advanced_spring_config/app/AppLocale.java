package com.mycompany.hw_l05_advanced_spring_config.app;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class AppLocale {

    private final Locale locale;

    public AppLocale(AppProperties props) {

        if (props.getLanguage() != null && props.getCountry() != null) {
            locale = new Locale(props.getLanguage(), props.getCountry());
        } else {
            locale = Locale.US;
        }
    }

    public Locale getLocale() {
        return locale;
    }
}
