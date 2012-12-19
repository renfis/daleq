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

package de.brands4friends.daleq.core.internal.formatting;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class DateFormatterTest {

    @Test
    public void printDateTime_should_beCorrectString() {
        final DateTime dateTime = new DateTime(2012, 11, 4, 7, 8, 9, 10);
        assertThat(DateFormatter.print(dateTime), is("2012-11-04 07:08:09.010"));
    }

    @Test
    public void printDate_should_beCorrectString() {
        final DateTime dateTime = new DateTime(2012, 11, 4, 7, 8, 9, 10);
        final Date date = dateTime.toDate();
        assertThat(DateFormatter.print(date), is("2012-11-04 07:08:09.010"));
    }

    @Test
    public void printLocalDate_should_beCorrectString() {
        final LocalDate localDate = new LocalDate(2012, 11, 4);
        assertThat(DateFormatter.print(localDate), is("2012-11-04"));
    }

    @Test
    public void printLocalTime_should_beCorrectString() {
        final LocalTime localTime = new LocalTime(7, 8, 9, 10);
        assertThat(DateFormatter.print(localTime), is("07:08:09"));
    }
}
