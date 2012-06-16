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

package de.brands4friends.daleq.internal.conversion;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;


public class DateTimeTypeConverterTest {

    private DateTimeTypeConverter dateTimeTypeConverter;

    @Before
    public void setUp() {
        dateTimeTypeConverter = new DateTimeTypeConverter();
    }

    @Test
    public void testConvert() {
        final DateTime date = new DateTime(2000, 12, 24, 1, 2, 3, 4);
        final String result = dateTimeTypeConverter.convert(date);
        assertThat(result, is("2000-12-24 01:02:03.004"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertForNullFails() {
        dateTimeTypeConverter.convert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWrongTypeFails() {
        dateTimeTypeConverter.convert(new Date());
    }

    @Test
    public void testGetResponsibleFor() {
        assertThat(dateTimeTypeConverter.getResponsibleFor(), is(DateTime.class.getClass()));
        assertNotSame(dateTimeTypeConverter.getResponsibleFor(), Date.class.getClass());
    }
}
