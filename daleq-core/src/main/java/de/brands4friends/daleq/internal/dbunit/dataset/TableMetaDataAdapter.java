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

import java.util.List;
import java.util.Map;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.NoSuchColumnException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.DataType;
import de.brands4friends.daleq.FieldType;
import de.brands4friends.daleq.TableType;

class TableMetaDataAdapter implements ITableMetaData {

    private static final Map<DataType, org.dbunit.dataset.datatype.DataType> DATA_TYPE_MAPPING =
            ImmutableMap.<DataType, org.dbunit.dataset.datatype.DataType>builder()
                    .put(DataType.VARCHAR, org.dbunit.dataset.datatype.DataType.VARCHAR)
                    .put(DataType.LONGVARCHAR, org.dbunit.dataset.datatype.DataType.LONGVARCHAR)
                    .put(DataType.NVARCHAR, org.dbunit.dataset.datatype.DataType.NVARCHAR)
                    .put(DataType.LONGNVARCHAR, org.dbunit.dataset.datatype.DataType.LONGNVARCHAR)
                    .put(DataType.CLOB, org.dbunit.dataset.datatype.DataType.CLOB)
                    .put(DataType.CHAR, org.dbunit.dataset.datatype.DataType.CHAR)
                    .put(DataType.NCHAR, org.dbunit.dataset.datatype.DataType.NCHAR)
                    .put(DataType.BOOLEAN, org.dbunit.dataset.datatype.DataType.BOOLEAN)
                    .put(DataType.BIT, org.dbunit.dataset.datatype.DataType.BIT)
                    .put(DataType.NUMERIC, org.dbunit.dataset.datatype.DataType.NUMERIC)
                    .put(DataType.DECIMAL, org.dbunit.dataset.datatype.DataType.DECIMAL)
                    .put(DataType.INTEGER, org.dbunit.dataset.datatype.DataType.INTEGER)
                    .put(DataType.TINYINT, org.dbunit.dataset.datatype.DataType.TINYINT)
                    .put(DataType.SMALLINT, org.dbunit.dataset.datatype.DataType.SMALLINT)
                    .put(DataType.BIGINT, org.dbunit.dataset.datatype.DataType.BIGINT)
                    .put(DataType.REAL, org.dbunit.dataset.datatype.DataType.REAL)
                    .put(DataType.DOUBLE, org.dbunit.dataset.datatype.DataType.DOUBLE)
                    .put(DataType.FLOAT, org.dbunit.dataset.datatype.DataType.FLOAT)
                    .put(DataType.BIGINT_AUX_LONG, org.dbunit.dataset.datatype.DataType.BIGINT_AUX_LONG)
                    .put(DataType.DATE, org.dbunit.dataset.datatype.DataType.DATE)
                    .put(DataType.TIME, org.dbunit.dataset.datatype.DataType.TIME)
                    .put(DataType.TIMESTAMP, org.dbunit.dataset.datatype.DataType.TIMESTAMP)
                    .put(DataType.VARBINARY, org.dbunit.dataset.datatype.DataType.VARBINARY)
                    .put(DataType.BINARY, org.dbunit.dataset.datatype.DataType.BINARY)
                    .put(DataType.LONGVARBINARY, org.dbunit.dataset.datatype.DataType.LONGVARBINARY)
                    .put(DataType.BLOB, org.dbunit.dataset.datatype.DataType.BLOB)
                    .build();

    private final TableType tableType;
    private final Column[] columnsCache;
    private final Map<String, Integer> nameToIndexCache;

    TableMetaDataAdapter(final TableType tableType) {
        this.tableType = tableType;

        final List<FieldType> fields = tableType.getFields();
        this.columnsCache = new Column[fields.size()];
        this.nameToIndexCache = Maps.newHashMap();
        int idx = 0;
        for (FieldType fieldType : fields) {
            columnsCache[idx] = createColumn(fieldType);
            nameToIndexCache.put(fieldType.getName(), idx);
            idx++;
        }
    }

    private Column createColumn(final FieldType fieldType) {
        return new Column(fieldType.getName(), DATA_TYPE_MAPPING.get(fieldType.getDataType()));
    }

    @Override
    public String getTableName() {
        return tableType.getName();
    }

    @Override
    public Column[] getColumns() throws DataSetException {
        return columnsCache;
    }

    @Override
    public Column[] getPrimaryKeys() throws DataSetException {
        // do we need this?
        return new Column[0];
    }

    @Override
    public int getColumnIndex(final String columnName) throws DataSetException {
        final Integer index = nameToIndexCache.get(columnName);
        if (index == null) {
            throw new NoSuchColumnException(tableType.getName(), columnName);
        }
        return index;
    }
}
