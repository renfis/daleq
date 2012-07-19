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

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import de.brands4friends.daleq.core.DataType;

/**
 *
 */
public final class DataTypeMapping {

    private static final BiMap<DataType, org.dbunit.dataset.datatype.DataType> MAPPING =
            ImmutableBiMap.<DataType, org.dbunit.dataset.datatype.DataType>builder()
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

    private DataTypeMapping() {

    }

    public static DataType toDaleq(final org.dbunit.dataset.datatype.DataType dbUnit) {
        return MAPPING.inverse().get(dbUnit);
    }

    public static org.dbunit.dataset.datatype.DataType toDbUnit(final DataType daleq) {
        return MAPPING.get(daleq);
    }
}
