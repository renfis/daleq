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

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

public class TimestampTemplateValueTest {

    private TimestampTemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new TimestampTemplateValue();
    }

    @Test
    public void should_render_a_datetime() {
        assertTransforming(0, new DateTime(1970, 1, 1, 1, 0, 1, DateTimeZone.UTC));
    }

    @Test
    public void should_render_increment_time() {
        assertTransforming(100, new DateTime(1970, 1, 1, 1, 1, 41, DateTimeZone.UTC));
    }

    private void assertTransforming(final int value, final DateTime expected) {
        final DateTime transform = (DateTime) templateValue.transform(value);
        assertThat(transform, Matchers.is(expected));
    }
}
