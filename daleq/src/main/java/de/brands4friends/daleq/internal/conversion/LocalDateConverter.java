package de.brands4friends.daleq.internal.conversion;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class LocalDateConverter implements TypeConverter {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .toFormatter();

    public String convert(final Object valueToConvert) {
        if (!(valueToConvert instanceof LocalDate)) {
            final String targetType = (valueToConvert == null) ? "null" : valueToConvert.getClass().getCanonicalName();
            final String msg = "LocalDateConverter tried to convert value [";
            throw new IllegalArgumentException(msg + valueToConvert + "] of type: [" + targetType + "]");
        }

        return createXMLDateTime((LocalDate) valueToConvert);
    }

    public Class<?> getResponsibleFor() {
        return LocalDate.class;
    }

    private static String createXMLDateTime(final LocalDate date) {
        return FORMATTER.print(date);
    }}
