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

import static de.brands4friends.daleq.Daleq.aRow;
import static de.brands4friends.daleq.Daleq.aTable;
import static de.brands4friends.daleq.internal.builder.ExampleTable.PROP_A;
import static de.brands4friends.daleq.internal.builder.ExampleTable.PROP_B;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.Context;
import de.brands4friends.daleq.DaleqBuildException;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TableDef;
import de.brands4friends.daleq.internal.types.TableType;
import de.brands4friends.daleq.internal.types.TableTypeFactory;

public class TableBuilderTest {

    private Context context;
    private StructureBuilder sb;

    @Before
    public void setUp() throws Exception {
        context = new SimpleContext();
        final TableType tableType = new TableTypeFactory().create(ExampleTable.class);
        sb = new StructureBuilder(tableType);
    }

    @Test
    public void aTableWithARow_should_beBuilt() {
        assertThat(
                aTable(ExampleTable.class).with(aRow(42)).build(context),
                is(sb.table(
                        sb.row(
                                sb.field(PROP_A, "42"),
                                sb.field(PROP_B, "42")
                        )
                ))
        );
    }

    @Test
    public void aTableWithSomeExplicitAddedRow_should_beBuilt() {
        assertThat(
                aTable(ExampleTable.class).with(aRow(1), aRow(2), aRow(3)).build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "1")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "2")),
                                sb.row(sb.field(PROP_A, "3"), sb.field(PROP_B, "3"))
                        ))
        );
    }

    @Test
    public void aTableWithSomeRows_should_beBuilt() {
        assertThat(
                aTable(ExampleTable.class).withSomeRows(Lists.newArrayList(1L, 2L, 3L)).build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "1")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "2")),
                                sb.row(sb.field(PROP_A, "3"), sb.field(PROP_B, "3"))
                        ))
        );
    }

    @Test
    public void aTableWithSomeRowsEllipsis_should_beBuilt() {
        assertThat(
                aTable(ExampleTable.class).withSomeRows(1L, 2L, 3L).build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "1")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "2")),
                                sb.row(sb.field(PROP_A, "3"), sb.field(PROP_B, "3"))
                        ))
        );
    }

    @Test
    public void aTableWithRowsUntil_should_beBuilt() {
        assertThat(
                aTable(ExampleTable.class).withRowsUntil(4).build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, "0")),
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "1")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "2")),
                                sb.row(sb.field(PROP_A, "3"), sb.field(PROP_B, "3"))
                        ))
        );
    }

    @Test
    public void allHaving_should_applyTheFieldToAllPreviouslyAddedRow() {
        assertThat(
                aTable(ExampleTable.class)
                        .with(
                                aRow(0).f(PROP_B, "0_ORG"),
                                aRow(1).f(PROP_B, "1_ORG"),
                                aRow(2).f(PROP_B, "2_ORG"))
                        .allHaving(PROP_B, "new")
                        .build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, "new")),
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "new")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "new"))
                        )
                )
        );
    }

    @Test
    public void allHaving_should_notApplyToRowsAddedAfter() {
        assertThat(
                aTable(ExampleTable.class)
                        .with(aRow(0).f(PROP_B, "0_ORG"))
                        .allHaving(PROP_B, "new")
                        .with(aRow(1).f(PROP_B, "1_ORG"), aRow(2).f(PROP_B, "2_ORG"))
                        .build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, "new")),
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "1_ORG")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "2_ORG"))
                        )
                )
        );
    }

    @Test
    public void having_withEmptyValues_should_leaveTheFieldsAsTheyAre() {
        assertThat(
                aTable(ExampleTable.class).withRowsUntil(3).having(PROP_B, Lists.newArrayList()).build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, "0")),
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "1")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "2"))
                        )
                )
        );
    }

    @Test
    public void having_withValuesAsMuchElementsAsTable_should_fillTheTableCompletely() {
        assertThat(
                aTable(ExampleTable.class)
                        .withRowsUntil(3)
                        .having(PROP_B, Lists.<Object>newArrayList("A", "B", "C"))
                        .build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, "A")),
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "B")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "C"))
                        )
                )
        );
    }

    @Test
    public void having_withValuesLessElementsThanTheTable_should_fillUpToThatValues() {
        assertThat(
                aTable(ExampleTable.class)
                        .withRowsUntil(3)
                        .having(PROP_B, Lists.<Object>newArrayList("A", "B"))
                        .build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, "A")),
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "B")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "2"))
                        )
                )
        );
    }

    @Test
    public void having_withValuesMoreElementsThanTheTable_should_fillTheTable() {
        final List<Object> values = Lists.newArrayList();
        values.add(null);
        assertThat(
                aTable(ExampleTable.class)
                        .withRowsUntil(1)
                        .having(PROP_B, values)
                        .build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, null))
                        )
                )
        );
    }

    @Test(expected = NullPointerException.class)
    public void having_withNullAsValues_should_fail() {
        aTable(ExampleTable.class)
                .withRowsUntil(3)
                .having(PROP_B, null)
                .build(context);
    }

    @Test
    public void having_withNullInValues_should_setThatRowToNull() {
        assertThat(
                aTable(ExampleTable.class)
                        .withRowsUntil(3)
                        .having(PROP_B, Lists.<Object>newArrayList("A", "B", "C", "D", "E"))
                        .build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, "A")),
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "B")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "C"))
                        )
                )
        );
    }

    @Test
    public void allHaving_onEmptyTable_should_doNothing() {
        assertThat(aTable(ExampleTable.class).allHaving(PROP_A, "FOO").build(context), is(sb.table()));
    }

    @TableDef("ANOTHER_TABLE")
    public static class AnotherTable {
        public static final FieldDef ANOTHER_FIELD = FieldDef.fd(DataType.INTEGER);
    }

    @Test(expected = DaleqBuildException.class)
    public void aTableWithFieldsFromAnotherTable_should_fail() {
        aTable(ExampleTable.class).with(aRow(1).f(AnotherTable.ANOTHER_FIELD, 123)).build(context);
    }
}
