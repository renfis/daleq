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

import org.junit.Test;

import de.brands4friends.daleq.core.test.EqualsAssert;

public class StringTemplateValueTest {

    @Test
    public void testHashCodeAndEquals() {
        EqualsAssert.assertProperEqualsAndHashcode(StringTemplateValue.class);
    }

    @Test
    public void renderATemplateWithAString_should_returnSubstitutedValue() {
        assertRendering("${_}", 42, "42");
    }

    @Test
    public void renderingATemplateWithoutVar_should_returnTemplateStr() {
        assertRendering("FOOBAR", 34, "FOOBAR");
    }

    @Test
    public void anEscapedVar_should_returnTheVar() {
        assertRendering("$${_}", 123, "${_}");
    }

    @Test
    public void replacingAVarInAstring_should_returnTheReplacedStr() {
        assertRendering("ABC${_}EFGH", 42, "ABC42EFGH");
    }

    @Test
    public void null_shouldBe_null() {
        assertRendering(null, 34, null);
    }

    private void assertRendering(final String template, final long binding, final Object expectedStr) {
        final TemplateValue templateValue = new StringTemplateValue(template);
        assertThat(templateValue.transform(binding), is(expectedStr));
    }
}
