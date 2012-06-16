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

package de.brands4friends.daleq.internal.template;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class CharTemplateValueTest {

    private CharTemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new CharTemplateValue();
    }

    @Test
    public void shouldBeA() {
        assertThat(templateValue.render(0), is("A"));
    }

    @Test
    public void shouldBeB() {
        assertThat(templateValue.render(1), is("B"));
    }

    @Test
    public void shouldBe_z() {
        assertThat(templateValue.render(57), is("z"));
    }

    @Test
    public void shouldBe_A_again() {
        assertThat(templateValue.render(58), is("A"));
    }

    @Test
    public void a141_shouldBe_Z() {
        assertThat(templateValue.render(141), is("Z"));
    }
}
