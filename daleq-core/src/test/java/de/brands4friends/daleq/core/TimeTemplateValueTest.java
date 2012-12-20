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

import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

public class TimeTemplateValueTest {

    private TimeTemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new TimeTemplateValue();
    }

    @Test
    public void testValue() {
        assertTransforming(0, new LocalTime(0, 0, 0));
        assertTransforming(1, new LocalTime(0, 0, 1));

        assertTransforming(60, new LocalTime(0, 1, 0));
        assertTransforming(3600, new LocalTime(1, 0, 0));

        assertTransforming(86399999L, new LocalTime(23, 59, 59));
        assertTransforming(86400000L, new LocalTime(0, 0, 0));
        assertTransforming(86400001L, new LocalTime(0, 0, 1));
    }

    private void assertTransforming(final long value, final LocalTime expected) {
        assertThat((LocalTime) templateValue.transform(value), is(expected));
    }
}
