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

package de.brands4friends.daleq.core.internal.builder;

import static de.brands4friends.daleq.core.Daleq.aRow;
import static de.brands4friends.daleq.core.Daleq.aTable;
import static de.brands4friends.daleq.core.internal.builder.ExampleTable.PROP_A;
import static de.brands4friends.daleq.core.internal.builder.ExampleTable.PROP_B;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.core.Context;
import de.brands4friends.daleq.core.Daleq;
import de.brands4friends.daleq.core.DaleqBuildException;
import de.brands4friends.daleq.core.DataType;
import de.brands4friends.daleq.core.FieldDef;
import de.brands4friends.daleq.core.Row;
import de.brands4friends.daleq.core.TableDef;
import de.brands4friends.daleq.core.TableType;
import de.brands4friends.daleq.core.internal.types.TableTypeFactory;

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

    @Test(expected = NullPointerException.class)
    public void aTableWithArrayIsNull_should_fail() {
        final Row[] nullRows = null;
        aTable(ExampleTable.class).with(nullRows);
    }

    @Test(expected = NullPointerException.class)
    public void aTableWithNullRow_should_fail() {
        aTable(ExampleTable.class).with(aRow(42), null);
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

    @Test(expected = NullPointerException.class)
    public void aTableWithSomeNull_should_throw() {
        final long[] nullArray = null;
        aTable(ExampleTable.class).withSomeRows(nullArray);
    }

    @Test(expected = NullPointerException.class)
    public void aTableWithSomeRowsAndOneIsNull_should_throw() {
        aTable(ExampleTable.class).withSomeRows(1L, 2L, (Long) null);
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
    public void aTableWithRowsBetween_should_beBuilt() {
        assertThat(
                aTable(ExampleTable.class).withRowsBetween(10, 15).build(context),
                is(sb.table(
                        sb.row(sb.field(PROP_A, "10"), sb.field(PROP_B, "10")),
                        sb.row(sb.field(PROP_A, "11"), sb.field(PROP_B, "11")),
                        sb.row(sb.field(PROP_A, "12"), sb.field(PROP_B, "12")),
                        sb.row(sb.field(PROP_A, "13"), sb.field(PROP_B, "13")),
                        sb.row(sb.field(PROP_A, "14"), sb.field(PROP_B, "14")),
                        sb.row(sb.field(PROP_A, "15"), sb.field(PROP_B, "15"))
                ))
        );
    }

    @Test
    public void aTableWithRowsBetween_andFromEqualsTo_should_beCorrect() {
        assertThat(
                aTable(ExampleTable.class).withRowsBetween(10, 10).build(context),
                is(sb.table(
                        sb.row(sb.field(PROP_A, "10"), sb.field(PROP_B, "10"))
                ))
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void aTableWithRowsBetween_butFromLessThanTo_should_fail() {
        aTable(ExampleTable.class).withRowsBetween(10, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aTableWithRowsBetween_butFromIsLessZero_should_fail() {
        aTable(ExampleTable.class).withRowsBetween(-1, 10);
    }

    @Test
    public void aTableWithRowsBetween_startingAtZero_should_beCorrect() {
        assertThat(
                aTable(ExampleTable.class).withRowsBetween(0, 1).build(context),
                is(sb.table(
                        sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, "0")),
                        sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "1"))
                ))
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void aTableWithRowsBetween_butToIsLessZero_should_fail() {
        aTable(ExampleTable.class).withRowsBetween(0, -1);
    }

    @Test
    public void aTableWithRowsBetween_toIsZero_should_beCorrect() {
        assertThat(
                aTable(ExampleTable.class).withRowsBetween(0, 0).build(context),
                is(sb.table(
                        sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, "0"))
                ))
        );
    }

    @Test
    public void allHaving_should_applyTheFieldToAllPreviouslyAddedRow() {
        final String newValue = "new";
        assertThat(
                aTable(ExampleTable.class)
                        .with(
                                aRow(0).f(PROP_B, "0_ORG"),
                                aRow(1).f(PROP_B, "1_ORG"),
                                aRow(2).f(PROP_B, "2_ORG"))
                        .allHaving(PROP_B, newValue)
                        .build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, newValue)),
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, newValue)),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, newValue))
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

    @Test(expected = NullPointerException.class)
    public void allHaving_withFieldNull_should_fail() {
        aTable(ExampleTable.class).withRowsBetween(1, 1).allHaving(null, "some value");
    }

    @Test(expected = DaleqBuildException.class)
    public void allHaving_withFieldDefNotInTable_should_fail() {
        aTable(ExampleTable.class)
                .withRowsUntil(1)
                .allHaving(Daleq.fd(DataType.CHAR).name("foo"), "bar")
                .build(context);
    }

    @Test
    public void allHaving_onEmptyTable_should_doNothing() {
        assertThat(aTable(ExampleTable.class).allHaving(PROP_A, "FOO").build(context), is(sb.table()));
    }

    @Test
    public void having_withEmptyValues_should_leaveTheFieldsAsTheyAre() {
        assertThat(
                aTable(ExampleTable.class).withRowsUntil(3).having(PROP_B).build(context),
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
                        .having(PROP_B, "A", "B", "C")
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
                        .having(PROP_B, "A", "B")
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
    public void having_withNullInValues_should_setThatRowToNull() {
        assertThat(
                aTable(ExampleTable.class)
                        .withRowsUntil(1)
                        .having(PROP_B, (Object) null)
                        .build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, null))
                        )
                )
        );
    }

    @Test(expected = NullPointerException.class)
    public void havingIterable_withNullAsValues_should_fail() {
        aTable(ExampleTable.class)
                .withRowsUntil(3)
                .havingIterable(PROP_B, null)
                .build(context);
    }

    @Test
    public void having_withValuesMoreElementsThanTheTable_should_fillTheTable() {
        assertThat(
                aTable(ExampleTable.class)
                        .withRowsUntil(3)
                        .having(PROP_B, "AA", "BB", "CC", "DD", "EE")
                        .build(context),
                is(
                        sb.table(
                                sb.row(sb.field(PROP_A, "0"), sb.field(PROP_B, "AA")),
                                sb.row(sb.field(PROP_A, "1"), sb.field(PROP_B, "BB")),
                                sb.row(sb.field(PROP_A, "2"), sb.field(PROP_B, "CC"))
                        )
                )
        );
    }

    @Test
    public void having_withFieldDefNotInTable_should_notFailWhenSet() {
        aTable(ExampleTable.class)
                .withRowsUntil(1)
                .having(Daleq.fd(DataType.CHAR).name("foo"), "bar");
    }

    @Test(expected = DaleqBuildException.class)
    public void having_withFieldDefNotInTable_should_failWhenBuilt() {
        aTable(ExampleTable.class)
                .withRowsUntil(1)
                .having(Daleq.fd(DataType.CHAR).name("foo"), "bar")
                .build(context);
    }

    @Test(expected = NullPointerException.class)
    public void having_withNullField_should_fail() {
        aTable(ExampleTable.class).withRowsBetween(1, 1).having(null);
    }

    @TableDef("ANOTHER_TABLE")
    public static class AnotherTable {
        public static final FieldDef ANOTHER_FIELD = Daleq.fd(DataType.INTEGER);
    }

    @Test(expected = DaleqBuildException.class)
    public void aTableWithFieldsFromAnotherTable_should_fail() {
        aTable(ExampleTable.class).with(aRow(1).f(AnotherTable.ANOTHER_FIELD, 123)).build(context);
    }

    @Test(expected = NullPointerException.class)
    public void build_with_null_should_fail() {
        aTable(ExampleTable.class).with(aRow(1)).build(null);
    }
}
