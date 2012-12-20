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

package de.brands4friends.daleq.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class DateTemplateValueTest {

    private DateTemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new DateTemplateValue();
    }

    @Test
    public void should_render_a_date() {
        assertTransform(0, new LocalDate(1970, 1, 1));
    }

    @Test
    public void render_should_increment_the_day() {
        assertTransform(10, new LocalDate(1970, 1, 11));
    }

    @Test
    public void renderMAX_VALUE_should_beMaximumDate() {
        assertTransform(DateTemplateValue.MAX_VALUE, new LocalDate(9999, 12, 31));
    }

    @Test
    public void renderOnePlusMAX_VALUE_should_beUnixStart() {
        assertTransform(DateTemplateValue.MAX_VALUE + 1, new LocalDate(1970, 1, 1));
    }

    @Test
    public void renderNegativeMAX_VALUE_should_valid() {
        assertTransform(-DateTemplateValue.MAX_VALUE, new LocalDate(1970, 1, 2));
    }

    @Test
    public void renderNegativeLong_should_renderValidDate() {
        assertTransform(-9223372036854775808L, new LocalDate(7616, 4, 12));
    }

    private void assertTransform(final long value, final LocalDate expected) {
        assertThat((LocalDate) templateValue.transform(value), is(expected));
    }
}
