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

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TemplateValuesTest {

    @Test
    public void base64TemplateValue_should_returnCorrectInstance() {
        assertThat(TemplateValues.base64(), instanceOf(Base64TemplateValue.class));
    }

    @Test
    public void charTemplateValue_should_returnCorrectInstance() {
        assertThat(TemplateValues.character(), instanceOf(CharTemplateValue.class));
    }

    @Test
    public void dateTemplateValue_should_returnCorrectInstance() {
        assertThat(TemplateValues.date(), instanceOf(DateTemplateValue.class));
    }

    @Test
    public void moduloTemplateValue_should_returnCorrectInstance() {
        assertThat(TemplateValues.modulo(12), instanceOf(ModuloTemplateValue.class));
    }

    @Test
    public void stringTemplateValue_should_returnCorrectInstance() {
        assertThat(TemplateValues.string("foo"), instanceOf(StringTemplateValue.class));
    }

    @Test
    public void timestampTemplateValue_should_returnCorrectInstance() {
        assertThat(TemplateValues.timestamp(), instanceOf(TimestampTemplateValue.class));
    }

    @Test
    public void timeTemplateValue_should_returnCorrectInstance() {
        assertThat(TemplateValues.time(), instanceOf(TimeTemplateValue.class));
    }

    @Test
    public void enumeratingTemplateValue_should_returnCorrectInstance() {
        assertThat(TemplateValues.enumeration("1", "2"), instanceOf(EnumeratingTemplateValue.class));
    }

    enum MyEnum { A, B, C }

    @Test
    public void enumeratingOnEnums_should_bePossible() {
        final TemplateValue templateValue = TemplateValues.enumeration(MyEnum.values());
        assertThat((MyEnum) templateValue.transform(0), is(MyEnum.A));
        assertThat((MyEnum) templateValue.transform(1), is(MyEnum.B));
        assertThat((MyEnum) templateValue.transform(2), is(MyEnum.C));
        assertThat((MyEnum) templateValue.transform(3), is(MyEnum.A));
    }
}
