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

package de.brands4friends.daleq.integration.beans;

import de.brands4friends.daleq.core.FieldDef;

public class TableProvider {

    private final Class<?> allTypesTable;
    private final Class<?> assertTable;
    private final FieldDef assertTableId;

    public TableProvider(
            final Class<?> allTypesTable,
            final Class<?> assertTable,
            final FieldDef assertTableId) {
        this.allTypesTable = allTypesTable;
        this.assertTable = assertTable;
        this.assertTableId = assertTableId;
    }

    public Class<?> allTypesTable() {
        return allTypesTable;
    }

    public Class<?> assertTable() {
        return assertTable;
    }

    public FieldDef assertTableId() {
        return assertTableId;
    }
}
