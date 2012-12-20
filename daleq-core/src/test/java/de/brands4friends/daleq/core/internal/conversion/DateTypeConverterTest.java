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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.junit.Assert;
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
        final String formatted = dateTypeConverter.convert(date);
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
        Assert.assertThat(dateTypeConverter.getResponsibleFor(), Matchers.typeCompatibleWith(Date.class));
        Assert.assertNotSame(dateTypeConverter.getResponsibleFor(), DateTime.class.getClass());
    }
}
