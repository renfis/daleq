package de.brands4friends.daleq.internal.conversion;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class LocalDateConverterTest {

    private LocalDateConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new LocalDateConverter();
    }

    @Test
    public void aLocalDate_should_beConverted() {
        assertThat(converter.convert(new LocalDate(2012, 10, 5)), is("2012-10-05"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void anotherObject_should_fail() {
        converter.convert(new Object());
    }

    @Test
    public void converter_should_beResponsibleForLocalDate() {
        assertThat(converter.getResponsibleFor(), Is.is(LocalDate.class.getClass()));
    }

}
