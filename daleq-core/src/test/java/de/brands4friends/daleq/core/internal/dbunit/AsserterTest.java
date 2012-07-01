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

package de.brands4friends.daleq.core.internal.dbunit;

import static de.brands4friends.daleq.core.Daleq.aRow;
import static de.brands4friends.daleq.core.Daleq.aTable;
import static org.easymock.EasyMock.expect;

import java.sql.SQLException;
import java.util.List;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.core.Daleq;
import de.brands4friends.daleq.core.DaleqException;
import de.brands4friends.daleq.core.DataType;
import de.brands4friends.daleq.core.FieldDef;
import de.brands4friends.daleq.core.Table;
import de.brands4friends.daleq.core.TableData;
import de.brands4friends.daleq.core.TableDef;
import de.brands4friends.daleq.core.internal.builder.SimpleContext;
import de.brands4friends.daleq.core.internal.dbunit.dataset.InMemoryDataSetFactory;
import junit.framework.ComparisonFailure;

public class AsserterTest extends EasyMockSupport {

    private IDatabaseConnection connection;
    private IDataSetFactory dataSetFactory;
    private ConnectionFactory connectionFactory;

    private Asserter asserter;

    @TableDef("FOO")
    public static class MyTable {
        public static final FieldDef ID = Daleq.fd(DataType.INTEGER);
        public static final FieldDef VALUE = Daleq.fd(DataType.VARCHAR);
    }

    @Before
    public void setUp() {
        dataSetFactory = new InMemoryDataSetFactory();
        connectionFactory = createMock(ConnectionFactory.class);
        connection = createMock(IDatabaseConnection.class);

        asserter = new Asserter(dataSetFactory, connectionFactory);
    }

    @Test
    public void assertTable_should_beAsserted() throws DataSetException, SQLException {
        final Table table = aTable(MyTable.class).with(aRow(0));
        expectDataSetFromDb(table);

        doAssert(table);
    }

    @Test(expected = ComparisonFailure.class)
    public void assertTable_should_failOnFailingAssertion() throws DataSetException, SQLException {
        final Table table = aTable(MyTable.class).with(aRow(0));
        final Table differentTable = aTable(MyTable.class).with(aRow(1));
        expectDataSetFromDb(table);

        doAssert(differentTable);
//        Assert.fail("The test should have failed, since we compare two different tables.");
    }

    @Test
    public void assertOnDifferentIgnoredColumn_should_notFail() throws SQLException, DataSetException {
        expectDataSetFromDb(
                aTable(MyTable.class).with(aRow(0).f(MyTable.ID, 42))
        );
        doAssert(
                aTable(MyTable.class).with(aRow(0).f(MyTable.ID, 23)),
                MyTable.ID
        );
    }

    @Test(expected = DaleqException.class)
    public void ignoredColumnDoesNoBelongToTable_should_fail() throws SQLException, DataSetException {
        final Table table = aTable(MyTable.class).withRowsUntil(10);
        expectDataSetFromDb(table);
        doAssert(table, Daleq.fd(DataType.BIGINT).name("something else"));
    }

    @Test(expected = DaleqException.class)
    public void ignoredColumContainsNull_should_fail() throws SQLException, DataSetException {
        final Table table = aTable(MyTable.class).withRowsUntil(10);
        expectDataSetFromDb(table);
        doAssert(table, MyTable.ID, null, MyTable.VALUE);
    }

    @Test(expected = DaleqException.class)
    public void gettingTheDataSetFailedWithSQLException_should_fail() throws SQLException, DataSetException {
        dataSetFactoryFailsWith(new SQLException("expected"));
    }

    private void dataSetFactoryFailsWith(final Exception expected) throws SQLException {
        final Table table = aTable(MyTable.class).withRowsUntil(10);
        expectConnection();
        expect(connection.createDataSet()).andThrow(expected);
        doAssert(table);
    }

    private void doAssert(final Table table, final FieldDef... ignoredColumns) {
        replayAll();
        asserter.assertTableInDatabase(toTableData(table), ignoredColumns);
        verifyAll();
    }

    private void expectDataSetFromDb(final Table table) throws DataSetException, SQLException {
        final IDataSet dataSetFromDb = dataSetFactory.create(toTableData(table));

        expectConnection();
        expect(connection.createDataSet()).andReturn(dataSetFromDb);
    }

    private void expectConnection() {
        expect(connectionFactory.createConnection()).andReturn(connection);
    }

    private List<TableData> toTableData(final Table table) {
        return Lists.newArrayList(table.build(new SimpleContext()));
    }
}
