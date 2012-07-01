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

package de.brands4friends.daleq.internal.dbunit.dataset;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.NoSuchColumnException;
import org.junit.Before;
import org.junit.Test;

import de.brands4friends.daleq.TableType;
import de.brands4friends.daleq.internal.types.TableTypeFactoryImpl;

public class TableMetaDataAdapterTest {

    private TableMetaDataAdapter tableMetaData;

    @Before
    public void setUp() {
        final TableType tableType = new TableTypeFactoryImpl().create(SomeTable.class);
        tableMetaData = new TableMetaDataAdapter(tableType);
    }

    @Test
    public void testGetTableName() {
        assertThat(tableMetaData.getTableName(), is("SOME_TABLE"));
    }

    @Test
    public void testGetColumns() throws DataSetException {
        final Column[] columns = tableMetaData.getColumns();
        assertThat(Arrays.asList(columns), contains(
                new Column("ID", org.dbunit.dataset.datatype.DataType.INTEGER),
                new Column("NAME", org.dbunit.dataset.datatype.DataType.VARCHAR))
        );
    }

    @Test
    public void testGetPrimaryKeys() throws DataSetException {
        assertThat(tableMetaData.getPrimaryKeys().length, is(0));
    }

    @Test
    public void getColumnIndexForId_should_beCorrect() throws DataSetException {
        assertThat(tableMetaData.getColumnIndex("ID"), is(0));
    }

    @Test
    public void getColumnIndexForName_should_beCorrect() throws DataSetException {
        assertThat(tableMetaData.getColumnIndex("NAME"), is(1));
    }

    @Test(expected = NoSuchColumnException.class)
    public void getColumnIndexForUnknown_should_throw() throws DataSetException {
        tableMetaData.getColumnIndex("FOO");
    }
}
