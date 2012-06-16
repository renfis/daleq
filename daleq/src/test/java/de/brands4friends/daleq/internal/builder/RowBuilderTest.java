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

package de.brands4friends.daleq.internal.builder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import de.brands4friends.daleq.Context;
import de.brands4friends.daleq.DaleqBuildException;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.internal.types.TableType;
import de.brands4friends.daleq.internal.types.TableTypeFactory;

public class RowBuilderTest {

    private Context context;
    private TableType tableType;
    private StructureBuilder sb;

    @Before
    public void setUp() throws Exception {
        context = new SimpleContext();
        tableType = new TableTypeFactory().create(ExampleTable.class);
        sb = new StructureBuilder(tableType);
    }

    @Test
    public void aRowWithJustProvidedProperties_should_beBuild() {
        assertThat(
                RowBuilder.aRow(23)
                        .f(ExampleTable.PROP_A, "FOO")
                        .f(ExampleTable.PROP_B, "BAR")
                        .build(context, tableType),
                is(sb.row(
                        sb.field(ExampleTable.PROP_A, "FOO"),
                        sb.field(ExampleTable.PROP_B, "BAR")
                ))
        );
    }

    @Test
    public void aRowWithJustDefaults_should_buildThatRow() {
        assertThat(
                RowBuilder.aRow(23).build(context, tableType),
                is(sb.row(
                        sb.field(ExampleTable.PROP_A, "23"),
                        sb.field(ExampleTable.PROP_B, "23")
                ))
        );
    }

    @Test(expected = DaleqBuildException.class)
    public void propertyInRowContainsProperyDefNotInTableStructure_should_fail() {
        final FieldDef bar = FieldDef.fd(DataType.VARCHAR);
        RowBuilder.aRow(42).f(bar, "foo").build(context, tableType);
    }
}
