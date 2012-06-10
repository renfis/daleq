package de.brands4friends.daleq.internal.conversion;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class TypeConversionImplTest {

    private TypeConversion conversion;

    @Before
    public void setUp() throws Exception {
        conversion = new TypeConversionImpl();
    }

    @Test
    public void dateConverter_should_beRegistered() {
        assertThat(conversion.convert(new Date(1234)), is(notNullValue()));
    }

    @Test
    public void localDateConverter_should_beRegistered() {
        assertThat(conversion.convert(new LocalDate(1234L)), is(notNullValue()));
    }

    @Test
    public void dateTimeConverter_should_beRegistered() {
        assertThat(conversion.convert(new DateTime(1234)), is(notNullValue()));
    }

    @Test
    public void aNonRegisteredConversion_should_returnToString() {
        assertThat(conversion.convert(new Object() {
            @Override
            public String toString() {
                return "hallo";
            }
        }), is("hallo"));
    }
}
