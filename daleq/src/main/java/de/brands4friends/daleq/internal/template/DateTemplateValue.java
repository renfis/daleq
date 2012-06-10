package de.brands4friends.daleq.internal.template;

import org.joda.time.LocalDate;

import de.brands4friends.daleq.TemplateValue;
import de.brands4friends.daleq.internal.conversion.LocalDateConverter;

final class DateTemplateValue implements TemplateValue {

    @Override
    public String render(final long value) {
        final LocalDate localDate = new LocalDate(0).plusDays((int) value);
        return LocalDateConverter.createXMLDateTime(localDate);
    }
}
