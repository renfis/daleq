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

package de.brands4friends.daleq.core;

import java.util.List;

import com.google.common.base.Optional;

/**
 * A data holding entity which represents a table according to Daleq's data model.
 * This class is used internally by Daleq and should not be accessed directly by clients.
 *
 * @see RowData
 * @see FieldData
 */
public interface TableData {
    /**
     * Returns the table's name.
     */
    String getName();

    /**
     * Returns an ordered list of all rows in the table.
     */
    List<RowData> getRows();

    /**
     * Maps rows to the value of the given <code>fieldName</code>.
     *
     * @param fieldName the name of the field in the rows of the table.
     * @return an iteration on all field values. If the field contains a <code>null</code> value,
     *         then an <code>Optional.absend()</code> is returned, otherwise the string representation of this value.
     * @throws NoSuchDaleqFieldException if such a field does not exist.
     */
    Iterable<Optional<String>> getValuesOfField(final String fieldName);

    TableType getTableType();
}
