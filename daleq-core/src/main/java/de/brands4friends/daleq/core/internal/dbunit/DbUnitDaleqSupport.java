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

import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.core.Context;
import de.brands4friends.daleq.core.DaleqException;
import de.brands4friends.daleq.core.DaleqSupport;
import de.brands4friends.daleq.core.FieldDef;
import de.brands4friends.daleq.core.Table;
import de.brands4friends.daleq.core.TableData;
import de.brands4friends.daleq.core.internal.formatting.MarkdownTableFormatter;
import de.brands4friends.daleq.core.internal.formatting.TableFormatter;

public final class DbUnitDaleqSupport implements DaleqSupport {

    private final IDataSetFactory dataSetFactory;
    private final ConnectionFactory connectionFactory;

    private DatabaseOperation insertOperation = DatabaseOperation.INSERT;
    private DatabaseOperation cleanInsertOperation = DatabaseOperation.CLEAN_INSERT;

    private final Context context;
    private final Asserter asserter;

    private TableFormatter tableFormatter = new MarkdownTableFormatter();

    public DbUnitDaleqSupport(
            final IDataSetFactory dataSetFactory,
            final ConnectionFactory connectionFactory,
            final Asserter asserter) {

        this.dataSetFactory = dataSetFactory;
        this.connectionFactory = connectionFactory;
        this.asserter = asserter;

        this.context = new SimpleContext();
    }

    void setInsertOperation(final DatabaseOperation insertOperation) {
        this.insertOperation = insertOperation;
    }

    void setCleanInsertOperation(final DatabaseOperation cleanInsertOperation) {
        this.cleanInsertOperation = cleanInsertOperation;
    }

    void setTableFormatter(final TableFormatter tableFormatter) {
        this.tableFormatter = tableFormatter;
    }

    private IDatabaseConnection createDatabaseConnection() {
        Preconditions.checkNotNull(connectionFactory, "connectionFactory is null.");
        return connectionFactory.createConnection();
    }

    @Override
    public void insertIntoDatabase(final Table... tables) {
        Preconditions.checkNotNull(insertOperation, "insertOperation");
        executeOnDatabase(insertOperation, tables);
    }

    @Override
    public void cleanInsertIntoDatabase(final Table... tables) {
        Preconditions.checkNotNull(cleanInsertOperation, "cleanInsertOperation");
        executeOnDatabase(cleanInsertOperation, tables);
    }

    @Override
    public void assertTableInDatabase(final Table table, final FieldDef... ignoreColumns) {
        Preconditions.checkNotNull(table);
        final List<TableData> allTables = toTables(table);
        asserter.assertTableInDatabase(allTables, ignoreColumns);
    }

    @Override
    public void printTable(final Table table, final PrintStream printer) throws IOException {
        Preconditions.checkNotNull(table);
        final TableData tableData = table.build(context);
        tableFormatter.formatTo(tableData, printer);
    }


    private void executeOnDatabase(final DatabaseOperation insertOperation1, final Table... tables) {
        try {
            final List<TableData> tableDatas = toTables(tables);
            doExecuteOnDatabase(insertOperation1, tableDatas);

        } catch (DatabaseUnitException e) {
            throw new DaleqException(e);
        } catch (SQLException e) {
            throw new DaleqException(e);
        }
    }

    private List<TableData> toTables(final Table... tables) {
        return Lists.transform(
                Arrays.asList(tables),
                new Function<Table, TableData>() {
                    @Override
                    public TableData apply(@Nullable final Table table) {
                        if (table == null) {
                            throw new IllegalArgumentException("table");
                        }
                        return table.build(context);
                    }
                });
    }

    private void doExecuteOnDatabase(final DatabaseOperation operation, final List<TableData> tables)
            throws DatabaseUnitException, SQLException {
        final IDataSet dbUnitDataset = dataSetFactory.create(tables);
        operation.execute(createDatabaseConnection(), dbUnitDataset);
    }
}
