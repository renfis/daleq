package de.brands4friends.daleq.internal.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTypeConverter implements TypeConverter {

    public String convert(final Object valueToConvert) {
        if (!(valueToConvert instanceof Date)) {
            final String targetType = valueToConvert == null ? "null" : valueToConvert.getClass().getCanonicalName();
            final String msg = "DateTypeConverter tried to convert value [";
            throw new IllegalArgumentException(msg + valueToConvert + "] of type: [" + targetType + "]");
        }

        return createXMLDateTime((Date) valueToConvert);
    }

    public Class<?> getResponsibleFor() {
        return Date.class;
    }

    private static String createXMLDateTime(final Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(date);
    }
}
