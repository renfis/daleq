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

public class DateTemplateValueTest {

    private DateTemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new DateTemplateValue();
    }

    @Test
    public void should_render_a_date() {
        assertThat(templateValue.render(0), is("1970-01-01"));
    }

    @Test
    public void render_should_increment_the_day() {
        assertThat(templateValue.render(10), is("1970-01-11"));
    }

    @Test
    public void renderMAX_VALUE_should_beMaximumDate() {
        assertThat(templateValue.render(DateTemplateValue.MAX_VALUE), is("9999-12-31"));
    }

    @Test
    public void renderOnePlusMAX_VALUE_should_beUnixStart() {
        assertThat(templateValue.render(DateTemplateValue.MAX_VALUE + 1), is("1970-01-01"));
    }

    @Test
    public void renderNegativeMAX_VALUE_should_valid() {
        assertThat(templateValue.render(-DateTemplateValue.MAX_VALUE), is("1970-01-02"));
    }

    @Test
    public void renderNegativeLong_should_renderValidDate() {
        assertThat(templateValue.render(-9223372036854775808L), is("7616-04-12"));
    }
}
