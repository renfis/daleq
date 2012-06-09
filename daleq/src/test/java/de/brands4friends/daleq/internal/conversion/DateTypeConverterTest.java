package de.brands4friends.daleq.internal.conversion;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class DateTypeConverterTest {

    private DateTypeConverter dateTypeConverter;

    @Before
    public void setUp() {
        dateTypeConverter = new DateTypeConverter();
    }

    @Test
    public void testConversion() throws ParseException {

        final Date date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS", Locale.US).parse("24-Feb-1998 17:39:35.123");
        final String formatted = (String) dateTypeConverter.convert(date);
        assertThat(formatted, is("1998-02-24 17:39:35.123"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertForNullFails() {
        dateTypeConverter.convert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWrongTypeFails() {
        final DateTypeConverter dateTypeConverter = new DateTypeConverter();
        dateTypeConverter.convert(new DateTime());
    }

    @Test
    public void testGetResponsibleFor() {
        final DateTypeConverter dateTypeConverter = new DateTypeConverter();
        assertThat(dateTypeConverter.getResponsibleFor(), is(Date.class.getClass()));
        assertNotSame(dateTypeConverter.getResponsibleFor(), DateTime.class.getClass());
    }
}
