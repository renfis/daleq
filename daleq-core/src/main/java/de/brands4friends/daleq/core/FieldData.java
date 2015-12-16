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

import com.google.common.base.Optional;

/**
 * A data holding entity which represents a Row's Field according to Daleq's data model.
 * This class is used internally by Daleq and should not be accessed directly by clients.
 *
 * @see TableData
 * @see RowData
 */
public interface FieldData {
    /**
     * The field's name
     *
     * @return The name
     */
    String getName();

    /**
     * Holds the field's value.
     *
     * @return If the field holds a <code>null</code> value, then <code>Optional.absend()</code> is
     *         returned, otherwise the actual value is returned.
     */
    Optional<String> getValue();
}
