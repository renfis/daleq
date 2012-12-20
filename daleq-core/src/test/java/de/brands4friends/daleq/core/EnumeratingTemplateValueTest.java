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

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class EnumeratingTemplateValueTest {

    private EnumeratingTemplateValue<String> templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new EnumeratingTemplateValue<String>(
                Lists.newArrayList("A", "B", "C", "D", "E")
        );
    }

    @Test
    public void transformPositiveValues() {

        assertTransform(0, "A");
        assertTransform(1, "B");
        assertTransform(2, "C");
        assertTransform(3, "D");
        assertTransform(4, "E");
        assertTransform(5, "A");
    }

    private void assertTransform(final int value, final String expected) {
        assertThat((String) templateValue.transform(value), is(expected));
    }

    @Test
    public void transformNegative() {
        assertTransform(-1, "B");
    }
}
