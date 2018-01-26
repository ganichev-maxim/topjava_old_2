package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Ganichev on 1/26/2018.
 */
public class LocalTimeConverter implements Converter<String, LocalTime> {

    private final DateTimeFormatter formatter;

    public LocalTimeConverter(String timeFormat) {
        formatter = DateTimeFormatter.ofPattern(timeFormat);
    }

    @Override
    public LocalTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        return LocalTime.parse(source, formatter);
    }
}
