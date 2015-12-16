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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

public class LocalTimeConverterTest {

    private LocalTimeConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new LocalTimeConverter();
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertNull_should_fail() {
        converter.convert(null);
    }

    @Test
    public void convertOfValidTime_should_returnString() {
        final LocalTime localTime = new LocalTime(1, 2, 3, 4);
        assertThat(converter.convert(localTime), is("01:02:03"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertAnotherClass_should_fail() {
        converter.convert(new Object());
    }

    @Test
    public void isResponsibleForLocalTime() {
        assertThat(converter.getResponsibleFor(), Matchers.typeCompatibleWith(LocalTime.class));
    }
}
