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

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.NoSuchColumnException;
import org.dbunit.dataset.RowOutOfBoundsException;

import de.brands4friends.daleq.core.FieldData;
import de.brands4friends.daleq.core.RowData;
import de.brands4friends.daleq.core.TableData;

class TableAdapter implements ITable {

    private final TableData tableData;
    private final ITableMetaData tableMetaData;

    TableAdapter(final TableData tableData) {
        this.tableData = tableData;
        this.tableMetaData = new TableMetaDataAdapter(tableData.getTableType());
    }

    @Override
    public ITableMetaData getTableMetaData() {
        return tableMetaData;
    }

    @Override
    public int getRowCount() {
        return tableData.getRows().size();
    }

    @Override
    public Object getValue(final int row, final String column) throws DataSetException {
        if (row >= tableData.getRows().size()) {
            throw new RowOutOfBoundsException();
        }
        final RowData rowData = tableData.getRows().get(row);
        if (!rowData.containsField(column)) {
            throw new NoSuchColumnException(tableData.getName(), column);
        }
        final FieldData field = rowData.getFieldBy(column);
        return field.getValue().orNull();
    }
}
