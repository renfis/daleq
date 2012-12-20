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

import java.math.BigInteger;

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
        assertTransform(0, 0);
    }

    private void assertTransform(final long value, final long expected) {
        assertThat((BigInteger) templateValue.transform(value), is(BigInteger.valueOf(expected)));
    }

    @Test
    public void oddValue_should_be1() {
        assertTransform(23, 1);
    }

    @Test
    public void evenValue_should_be0() {
        assertTransform(42, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void modulusZero_should_fail() {
        new ModuloTemplateValue(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void modulusLessThanZero_should_fail() {
        new ModuloTemplateValue(-1);
    }
}
