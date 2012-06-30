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
import static de.brands4friends.daleq.internal.builder.SimpleScenarioTest.MyTable.NAME;
import static de.brands4friends.daleq.internal.builder.SimpleScenarioTest.MyTable.VALUE;

import org.junit.Test;

import de.brands4friends.daleq.Daleq;
import de.brands4friends.daleq.DataType;
import de.brands4friends.daleq.FieldDef;

public class SimpleScenarioTest {

    @de.brands4friends.daleq.TableDef("foo")
    public static final class MyTable {
        public static final FieldDef ID = Daleq.fd(DataType.INTEGER);
        public static final FieldDef NAME = Daleq.fd(DataType.VARCHAR);
        public static final FieldDef VALUE = Daleq.fd(DataType.BIT);
        public static final FieldDef MODIFIED = Daleq.fd(DataType.TIMESTAMP);
    }

    @Test
    public void justTwoRows() {
        aTable(MyTable.class).with(aRow(1), aRow(2));
    }

    @Test
    public void rowsWithProperties() {
        aTable(MyTable.class)
                .with(
                        aRow(42).f(NAME, "foo").f(VALUE, "1"),
                        aRow(23).f(NAME, "bar")
                );
    }
}
