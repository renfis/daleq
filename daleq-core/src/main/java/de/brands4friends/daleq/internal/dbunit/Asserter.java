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

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.dbunit.DatabaseUnitException;
import org.dbunit.assertion.DbUnitAssert;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.DaleqException;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.FieldType;
import de.brands4friends.daleq.TableData;
import de.brands4friends.daleq.TableType;

public class Asserter {

    private final IDataSetFactory dataSetFactory;
    private final ConnectionFactory connectionFactory;
    private final DbUnitAssert dbUnitAssert;

    public Asserter(
            final IDataSetFactory dataSetFactory,
            final ConnectionFactory connectionFactory) {
        this.dataSetFactory = dataSetFactory;
        this.connectionFactory = connectionFactory;
        this.dbUnitAssert = new DbUnitAssert();
    }

    public void assertTableInDatabase(final List<TableData> allTables, final FieldDef... ignoreColumns) {
        try {
            final TableData tableData = allTables.get(0);
            final TableType tableType = tableData.getTableType();
            final String tableName = tableData.getName();

            final IDataSet expectedDataSet = dataSetFactory.create(allTables);
            final ITable expectedTable = expectedDataSet.getTable(tableName);
            final IDataSet actualDataSet = createDatabaseConnection().createDataSet();
            final ITable actualTable = actualDataSet.getTable(tableName);

            final List<String> ignoredColumnNames = toColumnNames(ignoreColumns, tableType);
            final String[] ignoredColumnNamesAsArray = ignoredColumnNames.toArray(
                    new String[ignoredColumnNames.size()]
            );

            dbUnitAssert.assertEqualsIgnoreCols(expectedTable, actualTable, ignoredColumnNamesAsArray);

        } catch (DataSetException e) {
            throw new DaleqException(e);
        } catch (SQLException e) {
            throw new DaleqException(e);
        } catch (DatabaseUnitException e) {
            throw new DaleqException(e);
        }
    }

    private IDatabaseConnection createDatabaseConnection() {
        return connectionFactory.createConnection();
    }

    private List<String> toColumnNames(final FieldDef[] ignoreColumns, final TableType tableType) {
        return Lists.transform(Arrays.asList(ignoreColumns), new Function<FieldDef, String>() {
            @Override
            public String apply(@Nullable final FieldDef fieldDef) {
                if (fieldDef == null) {
                    throw new DaleqException("Cannot ignore null column");
                }
                final FieldType field = tableType.findFieldBy(fieldDef);
                if (field == null) {
                    final String msg = String.format("The FieldDef '%s' is not contained in Table '%s'.",
                            fieldDef.getName(), tableType.getName());
                    throw new DaleqException(msg);
                }
                return field.getName();
            }
        });
    }

}
