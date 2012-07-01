/*
 * Copyright 2012 brands4friends, Private Sale GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.brands4friends.daleq.core.internal.conversion;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 * Converts a DateTime instance to a string representation that can
 * be handled by Daleq/DBUnit.
 */
public class DateTimeTypeConverter implements TypeConverter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss.SSS")
            .toFormatter();

    public String convert(final Object valueToConvert) {
        if (!(valueToConvert instanceof DateTime)) {
            final String targetType = (valueToConvert == null) ? "null" : valueToConvert.getClass().getCanonicalName();
            final String msg = "DateTimeTypeConverter tried to convert value [";
            throw new IllegalArgumentException(msg + valueToConvert + "] of type: [" + targetType + "]");
        }

        return createXMLDateTime((DateTime) valueToConvert);
    }

    public Class<?> getResponsibleFor() {
        return DateTime.class;
    }

    public static String createXMLDateTime(final DateTime date) {
        return DATE_TIME_FORMATTER.print(date);
    }
}
