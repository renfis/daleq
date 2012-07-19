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

package de.brands4friends.daleq.core.internal.types;

import de.brands4friends.daleq.core.Daleq;
import de.brands4friends.daleq.core.DataType;
import de.brands4friends.daleq.core.FieldDef;
import de.brands4friends.daleq.core.TableDef;
import de.brands4friends.daleq.core.TableType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class ClassBasedTableTypeResolverTest {
    private ClassBasedTableTypeResolver resolver;
    private NamedTableTypeReference anotherReference;

    @Before
    public void setUp() throws Exception {
        resolver = new ClassBasedTableTypeResolver();
        anotherReference = new NamedTableTypeReference("foo");
    }

    @TableDef("MY_TABLE")
    public static final class Table {
        public static final FieldDef ID = Daleq.fd(DataType.BIGINT);
    }

    @Test
    public void canResolve_should_handleClassBasedTableTypeReference() {
        assertThat(resolver.canResolve(ClassBasedTableTypeReference.of(Table.class)), is(true));
    }

    @Test
    public void canResolve_should_notHandleOtherReferences() {
        assertThat(resolver.canResolve(anotherReference), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolveANonClassBasedReference_should_throw() {
        resolver.resolve(anotherReference);
    }

    @Test
    public void resolve_should_returnATable() {
        final TableType table = resolver.resolve(ClassBasedTableTypeReference.of(Table.class));
        assertThat(table.getName(), is("MY_TABLE"));
    }
}
