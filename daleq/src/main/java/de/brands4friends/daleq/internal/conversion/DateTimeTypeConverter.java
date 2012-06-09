package de.brands4friends.daleq.internal.conversion;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 * Converts a DateTime instance to a string representation that can
 * be handled by Daleq/DBUnit.
 */
public class DateTimeTypeConverter implements TypeConverter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss.SSS").toFormatter();

    public String convert(final Object valueToConvert) {
        if(!(valueToConvert instanceof DateTime)) {
            throw new IllegalArgumentException("DateTimeTypeConverter tried to convert value [" + valueToConvert + "] of type: [" + (valueToConvert == null ? null : valueToConvert.getClass()) + "]");
        }

        return createXMLDateTime((DateTime)valueToConvert);
    }

    public Class<?> getResponsibleFor() {
        return DateTime.class;
    }

    private static String createXMLDateTime(final DateTime date) {
        return DATE_TIME_FORMATTER.print(date);
    }
}
