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

package de.brands4friends.daleq.internal.formatting;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.Daleq;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TableData;
import de.brands4friends.daleq.TableDef;
import de.brands4friends.daleq.internal.builder.SimpleContext;

public class MarkdownTableFormatterTest {

    private SimpleContext context;
    private TableFormatter formatter;

    @TableDef("THE_TABLE")
    public static final class TheTable {
        public static final FieldDef ID = Daleq.fd(DataType.INTEGER);
        public static final FieldDef NAME = Daleq.fd(DataType.VARCHAR);
        public static final FieldDef STUFF = Daleq.fd(DataType.VARCHAR);
    }

    @Before
    public void setUp() throws Exception {
        context = new SimpleContext();
        formatter = new MarkdownTableFormatter();
    }

    @Test
    public void format_should_printHeaders() {
        final TableData table = Daleq.aTable(TheTable.class).build(context);
        assertThat(
                formatter.format(table),
                is(lines(
                        "| ID | NAME | STUFF |",
                        "|---:|-----:|------:|")
                )
        );
    }

    private String lines(final String... lines) {
        return Joiner.on("\n").join(lines) + "\n";
    }

    @TableDef("COLUMN_LENGTH")
    public static final class ColumnLength {
        public static final FieldDef COLUMN = Daleq.fd(DataType.VARCHAR);
    }

    @Test
    public void should_alignToTheLongestValue() {
        final TableData table = Daleq.aTable(ColumnLength.class)
                .withRowsUntil(5)
                .having(ColumnLength.COLUMN, Lists.<Object>newArrayList(
                        "A",
                        "ABCDEFGHIJ",
                        "ABCDEF",
                        "ABCDEFGHI",
                        ""
                ))
                .build(context);
        assertThat(
                formatter.format(table),
                is(lines(
                        "| COLUMN     |",
                        "|-----------:|",
                        "|          A |",
                        "| ABCDEFGHIJ |",
                        "|     ABCDEF |",
                        "|  ABCDEFGHI |",
                        "|            |"
                )
                )
        );

    }

}
