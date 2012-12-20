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

/**
 * A data holding entity which represents a table row according to Daleq's data model.
 * This class is used internally by Daleq and should not be accessed directly by clients.
 *
 * @see TableData
 * @see FieldData
 */
public interface RowData {

    /**
     * If the row contains a Field with the given <code>fieldName</code>, the respective {@link FieldData} is returned.
     *
     * @param fieldName the name of a field, which should exist in the respective row.
     * @throws NoSuchDaleqFieldException if such a field does not exist in the RowData
     */
    FieldData getFieldBy(final String fieldName);

    /**
     * Returns true, if such a field exists, otherwise false.
     *
     * @param fieldName the name of a field, which should exist in the respective row.
     */
    boolean containsField(final String fieldName);
}
