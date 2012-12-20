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
import org.joda.time.LocalDate;
import org.junit.Assert;
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
        Assert.assertThat(converter.getResponsibleFor(), Matchers.typeCompatibleWith(LocalDate.class));
    }

}
