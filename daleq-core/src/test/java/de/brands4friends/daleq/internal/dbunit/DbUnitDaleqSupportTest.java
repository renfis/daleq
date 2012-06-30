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

package de.brands4friends.daleq.internal.dbunit;

import static de.brands4friends.daleq.Daleq.aRow;
import static de.brands4friends.daleq.Daleq.aTable;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.operation.DatabaseOperation;
import org.easymock.Capture;
import org.easymock.EasyMockSupport;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.TableData;
import de.brands4friends.daleq.TableDef;
import de.brands4friends.daleq.internal.builder.SimpleContext;
import junit.framework.ComparisonFailure;

public class DbUnitDaleqSupportTest extends EasyMockSupport {

    private IDatabaseConnection connection;
    private IDataSetFactory dataSetFactory;

    @TableDef("FOO")
    public static class MyTable {
        public static final FieldDef ID = FieldDef.fd(DataType.INTEGER);
        public static final FieldDef VALUE = FieldDef.fd(DataType.VARCHAR);
    }

    private ConnectionFactory connectionFactory;
    private DbUnitDaleqSupport daleqSupport;
    private DatabaseOperation insertOperation;

    @Before
    public void setUp() throws Exception {
        daleqSupport = new DbUnitDaleqSupport();
        connectionFactory = createMock(ConnectionFactory.class);
        insertOperation = createMock(DatabaseOperation.class);
        daleqSupport.setConnectionFactory(connectionFactory);
        daleqSupport.setInsertOperation(insertOperation);
        connection = createMock(IDatabaseConnection.class);
        dataSetFactory = new FlatXmlIDataSetFactory();
    }

    @Test
    public void inserIntoDatabase_should_insertAnIDataSetWithDbUnit() throws SQLException, DatabaseUnitException {
        expectConnection();
        final Capture<IDataSet> capturedDataset = new Capture<IDataSet>();
        insertOperation.execute(eq(connection), capture(capturedDataset));

        replayAll();
        daleqSupport.insertIntoDatabase(
                aTable(MyTable.class).with(
                        aRow(0).f(MyTable.VALUE, "val0"),
                        aRow(1).f(MyTable.VALUE, "val1")
                )
        );
        verifyAll();

        final IDataSet dataSet = capturedDataset.getValue();
        assertThat(dataSet.getTableNames(), arrayContaining("FOO"));
        final ITable table = dataSet.getTable("FOO");
        assertThat(table.getValue(0, "ID"), Matchers.is((Object) "0"));
        assertThat(table.getValue(0, "VALUE"), Matchers.is((Object) "val0"));
        assertThat(table.getValue(1, "ID"), Matchers.is((Object) "1"));
        assertThat(table.getValue(1, "VALUE"), Matchers.is((Object) "val1"));
    }

    @Test
    public void assertTable_should_beAsserted() throws DataSetException, SQLException {
        final Table table = aTable(MyTable.class).with(aRow(0));
        final IDataSet dataSetFromDb = dataSetFactory.create(toTableData(table));

        expectConnection();
        expect(connection.createDataSet()).andReturn(dataSetFromDb);

        replayAll();
        daleqSupport.assertTableInDatabase(table);
        verifyAll();
    }

    @Test(expected = ComparisonFailure.class)
    public void assertTable_should_failOnFailingAssertion() throws DataSetException, SQLException {
        final Table table = aTable(MyTable.class).with(aRow(0));
        final Table differentTable = aTable(MyTable.class).with(aRow(1));
        final IDataSet dataSetFromDb = dataSetFactory.create(toTableData(table));

        expectConnection();
        expect(connection.createDataSet()).andReturn(dataSetFromDb);

        replayAll();

        daleqSupport.assertTableInDatabase(differentTable);
        Assert.fail("The test should have failed, since we compare two different tables.");

    }

    private void expectConnection() {
        expect(connectionFactory.createConnection()).andReturn(connection);
    }

    private List<TableData> toTableData(final Table table) {
        return Lists.newArrayList(table.build(new SimpleContext()));
    }
}
