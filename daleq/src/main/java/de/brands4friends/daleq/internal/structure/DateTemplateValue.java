package de.brands4friends.daleq.internal.structure;

import org.joda.time.LocalDate;

import de.brands4friends.daleq.internal.conversion.LocalDateConverter;

public class DateTemplateValue implements TemplateValue {

    @Override
    public String render(final long value) {
        LocalDate localDate = new LocalDate(0).plusDays((int) value);
        return LocalDateConverter.createXMLDateTime(localDate);
    }
}
