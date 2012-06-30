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

package de.brands4friends.daleq.internal.types;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TableDef;
import de.brands4friends.daleq.TableType;
import de.brands4friends.daleq.TableTypeFactory;
import de.brands4friends.daleq.TemplateValue;

public class TableTypeFactoryTest {

    private TableTypeFactory factory;

    @TableDef("MY_TABLE")
    static class MyTable {
        public static final FieldDef ID = FieldDef.fd(DataType.INTEGER);
    }

    @Before
    public void setUp() throws Exception {
        factory = new TableTypeFactoryImpl();
    }

    @Test
    public void createOfMyTable_should_returnTableStructure() {

        final TableType tableType = factory.create(MyTable.class);
        final TableType expected = new TableTypeImpl("MY_TABLE",
                new FieldTypeImpl("ID", DataType.INTEGER, Optional.<TemplateValue>absent(), MyTable.ID));

        assertThat(tableType, is(expected));
    }

    static class WithoutAnnotation {
        public static final FieldDef ID = FieldDef.fd(DataType.INTEGER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithoutAnnotation_should_fail() {
        factory.create(WithoutAnnotation.class);
    }

    @TableDef("MY_TABLE")
    static class WithoutPropertyDefs {

    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithoutPropertyDefs_should_fail() {
        factory.create(WithoutPropertyDefs.class);
    }
}
