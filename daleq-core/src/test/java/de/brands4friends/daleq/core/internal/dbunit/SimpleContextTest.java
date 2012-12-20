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

package de.brands4friends.daleq.core.internal.dbunit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.brands4friends.daleq.core.internal.conversion.TypeConversion;
import de.brands4friends.daleq.core.internal.template.TemplateValueFactory;
import de.brands4friends.daleq.core.internal.types.TableTypeRepository;

public class SimpleContextTest {

    private SimpleContext ctx;

    class Foo {

    }

    @Before
    public void setUp() throws Exception {
        ctx = new SimpleContext();
    }

    @Test(expected = IllegalArgumentException.class)
    public void gettingUnknownService_should_fail() {
        ctx.getService(Foo.class);
    }

    @Test
    public void getTypeConversion_should_beRegistered() {
        assertThat(ctx.getService(TypeConversion.class), is(notNullValue()));
    }

    @Test
    public void getTemplateValueFactory_should_beRegistered() {
        assertThat(ctx.getService(TemplateValueFactory.class), is(notNullValue()));
    }

    @Test
    public void getTableTypeRepository_should_beRegistered() {
        assertThat(ctx.getService(TableTypeRepository.class), is(notNullValue()));
    }
}
