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
import de.brands4friends.daleq.core.DaleqBuildException;
import de.brands4friends.daleq.core.DataType;
import de.brands4friends.daleq.core.FieldDef;
import de.brands4friends.daleq.core.TableDef;
import de.brands4friends.daleq.core.TableType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class CachingTableTypeRepositoryTest {
    private CachingTableTypeRepository repository;

    @TableDef("MY_TABLE")
    public static final class Table {
        public static final FieldDef ID = Daleq.fd(DataType.BIGINT);
    }

    @Before
    public void setUp() throws Exception {
        repository = new CachingTableTypeRepository();
    }

    @Test
    public void get_should_returnATableType() {
        final TableType table = repository.get(ClassBasedTableTypeReference.of(Table.class));
        assertThat(table.getName(), is("MY_TABLE"));
    }

    @Test
    public void getATableTwice_should_returnSameInstance() {
        final TableType first = repository.get(ClassBasedTableTypeReference.of(Table.class));
        final TableType second = repository.get(ClassBasedTableTypeReference.of(Table.class));
        assertThat(first, is(sameInstance(second)));
    }

    @Test(expected = DaleqBuildException.class)
    public void noResolverForReference_should_throw() {
        repository.get(new NamedTableTypeReference("foo"));
    }
}
