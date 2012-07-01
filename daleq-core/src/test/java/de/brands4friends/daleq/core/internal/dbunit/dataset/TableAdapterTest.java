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

package de.brands4friends.daleq.core.internal.dbunit.dataset;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.NoSuchColumnException;
import org.dbunit.dataset.RowOutOfBoundsException;
import org.junit.Before;
import org.junit.Test;

import de.brands4friends.daleq.core.Daleq;
import de.brands4friends.daleq.core.TableData;
import de.brands4friends.daleq.core.internal.builder.SimpleContext;

public class TableAdapterTest {

    public static final String ID = "ID";
    private TableData tableData;
    private TableAdapter tableAdapter;

    @Before
    public void setUp() {
        tableData = Daleq.aTable(SomeTable.class).withRowsUntil(10).build(new SimpleContext());
        tableAdapter = new TableAdapter(tableData);
    }

    @Test
    public void testGetTableMetaData() {
        assertThat(tableAdapter.getTableMetaData(), is(notNullValue()));
    }

    @Test
    public void testGetRowCount() {
        assertThat(tableAdapter.getRowCount(), is(10));
    }

    @Test
    public void getValue_should_returnThatValue() throws DataSetException {
        final String value = tableData.getRows().get(0).getFieldBy(ID).getValue().get();
        assertThat((String) tableAdapter.getValue(0, ID), is(value));
    }

    @Test(expected = RowOutOfBoundsException.class)
    public void getValueOfNonExistingRow_should_throw() throws DataSetException {
        tableAdapter.getValue(11, ID);
    }

    @Test(expected = NoSuchColumnException.class)
    public void getValueOfUnknownColumn_should_throw() throws DataSetException {
        tableAdapter.getValue(0, "FOO");
    }
}
