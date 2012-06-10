package de.brands4friends.daleq.internal.template;

import org.joda.time.DateTime;

import de.brands4friends.daleq.internal.conversion.DateTimeTypeConverter;

final class TimestampTemplateValue implements TemplateValue {
    @Override
    public String render(final long value) {
        final DateTime dateTime = new DateTime(0).plusSeconds((int) value);
        return DateTimeTypeConverter.createXMLDateTime(dateTime);
    }
}
