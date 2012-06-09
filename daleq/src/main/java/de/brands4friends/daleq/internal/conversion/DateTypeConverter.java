package de.brands4friends.daleq.internal.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTypeConverter implements TypeConverter {

    public String convert(final Object valueToConvert) {
        if(! (valueToConvert instanceof Date)) {
            throw new IllegalArgumentException("DateTypeConverter tried to convert value [" + valueToConvert + "] of type: [" + (valueToConvert != null ? valueToConvert.getClass() : null) + "]");
        }

        return createXMLDateTime((Date)valueToConvert);
    }

    public Class<?> getResponsibleFor() {
        return Date.class;
    }

    private static String createXMLDateTime(final Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(date);
    }
}
