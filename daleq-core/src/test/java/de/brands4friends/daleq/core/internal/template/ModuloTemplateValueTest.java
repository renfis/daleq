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

import nl.jqno.equalsverifier.EqualsVerifier;

public class ModuloTemplateValueTest {

    private ModuloTemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new ModuloTemplateValue(2);
    }

    @Test
    public void testHashCodeAndEquals() {
        EqualsVerifier.forClass(ModuloTemplateValue.class).verify();
    }

    @Test
    public void value_should_beRendered() {
        assertThat(templateValue.render(0), is("0"));
    }

    @Test
    public void oddValue_should_be1() {
        assertThat(templateValue.render(23), is("1"));
    }

    @Test
    public void evenValue_should_be0() {
        assertThat(templateValue.render(42), is("0"));
    }
}
