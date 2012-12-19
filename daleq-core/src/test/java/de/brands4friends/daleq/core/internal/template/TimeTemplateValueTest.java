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

package de.brands4friends.daleq.core.internal.template;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
        assertThat(templateValue.render(0), is("00:00:00"));
        assertThat(templateValue.render(1), is("00:00:01"));
        assertThat(templateValue.render(60), is("00:01:00"));
        assertThat(templateValue.render(3600), is("01:00:00"));
        assertThat(templateValue.render(86399999L), is("23:59:59"));
        assertThat(templateValue.render(86400000L), is("00:00:00"));
        assertThat(templateValue.render(86400001L), is("00:00:01"));
    }
}
