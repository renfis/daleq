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
import static de.brands4friends.daleq.core.internal.dbunit.ContextFactory.context;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.core.Daleq;
import de.brands4friends.daleq.core.DataType;
import de.brands4friends.daleq.core.FieldDef;
import de.brands4friends.daleq.core.Table;
import de.brands4friends.daleq.core.TableData;
import de.brands4friends.daleq.core.TableDef;
import de.brands4friends.daleq.core.internal.dbunit.dataset.InMemoryDataSetFactory;
import de.brands4friends.daleq.core.internal.formatting.TableFormatter;

// DaleqSupport should wrap the access to daleq. yes this might produce too much coupling, but for now that's the
// way we go here.
@SuppressWarnings("PMD.ExcessiveImports")
public class DbUnitDaleqSupportTest extends EasyMockSupport {

    private IDatabaseConnection connection;
    private Asserter asserter;
    private TableFormatter tableFormatter;
    private Table exampleTable;

    @TableDef("FOO")
    public static class MyTable {
        public static final FieldDef ID = Daleq.fd(DataType.INTEGER);
        public static final FieldDef VALUE = Daleq.fd(DataType.VARCHAR);
    }

    private ConnectionFactory connectionFactory;
    private DbUnitDaleqSupport daleqSupport;
    private DatabaseOperation insertOperation;
    private DatabaseOperation cleanInsertOperation;

    @Before
    public void setUp() throws Exception {
        connectionFactory = createMock(ConnectionFactory.class);
        connection = createMock(IDatabaseConnection.class);
        final IDataSetFactory dataSetFactory = new InMemoryDataSetFactory();
        asserter = createMock(Asserter.class);
        daleqSupport = new DbUnitDaleqSupport(dataSetFactory, connectionFactory, asserter);

        insertOperation = createMock(DatabaseOperation.class);
        cleanInsertOperation = createMock(DatabaseOperation.class);
        tableFormatter = createMock(TableFormatter.class);

        daleqSupport.setInsertOperation(insertOperation);
        daleqSupport.setCleanInsertOperation(cleanInsertOperation);
        daleqSupport.setTableFormatter(tableFormatter);

        exampleTable = aTable(MyTable.class).with(
                aRow(0).f(MyTable.VALUE, "val0"),
                aRow(1).f(MyTable.VALUE, "val1")
        );
    }

    @Test
    public void insertIntoDatabase_should_insertAnIDataSetWithDbUnit() throws SQLException, DatabaseUnitException {
        expectConnection();
        final Capture<IDataSet> capturedDataset = new Capture<IDataSet>();
        insertOperation.execute(eq(connection), capture(capturedDataset));

        replayAll();
        daleqSupport.insertIntoDatabase(exampleTable);
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
    public void cleanInsertIntoDatabase_should_delegateToCleanInsertOperation()
            throws DatabaseUnitException, SQLException {
        expectConnection();
        cleanInsertOperation.execute(eq(connection), EasyMock.anyObject(IDataSet.class));

        replayAll();
        daleqSupport.cleanInsertIntoDatabase(exampleTable);
        verifyAll();
    }

    @Test
    public void assertTable_should_delegate() throws DataSetException, SQLException {
        final Table table = aTable(MyTable.class).with(aRow(0));
        final List<TableData> tables = toTableData(table);
        asserter.assertTableInDatabase(tables, MyTable.ID, MyTable.VALUE);

        replayAll();
        daleqSupport.assertTableInDatabase(table, MyTable.ID, MyTable.VALUE);
        verifyAll();
    }

    @Test
    public void printTable_should_delegateToTableFormatter() throws IOException {

        final PrintStream printStream = createMock(PrintStream.class);

        tableFormatter.formatTo(exampleTable.build(ContextFactory.context()), printStream);

        replayAll();
        daleqSupport.printTable(exampleTable, printStream);
        verifyAll();
    }

    private void expectConnection() {
        expect(connectionFactory.createConnection()).andReturn(connection);
    }

    private List<TableData> toTableData(final Table table) {
        return Lists.newArrayList(table.build(context()));
    }
}
