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

import java.util.List;
import java.util.Map;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.NoSuchColumnException;

import com.google.common.collect.Maps;

import de.brands4friends.daleq.core.FieldType;
import de.brands4friends.daleq.core.TableType;
import de.brands4friends.daleq.core.internal.dbunit.DataTypeMapping;

class TableMetaDataAdapter implements ITableMetaData {

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
        return new Column(fieldType.getName(), DataTypeMapping.toDbUnit(fieldType.getDataType()));
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
