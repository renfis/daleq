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

package de.brands4friends.daleq.core.internal.types;

import com.google.common.base.Objects;

import de.brands4friends.daleq.core.TableTypeReference;

/**
 *
 */
public final class ClassBasedTableTypeReference<T> implements TableTypeReference {

    private final Class<T> table;

    private ClassBasedTableTypeReference(final Class<T> table) {
        this.table = table;
    }

    public Class<T> getTable() {
        return table;
    }

    public static <T> TableTypeReference of(final Class<T> table) {
        return new ClassBasedTableTypeReference<T>(table);
    }

    private String getTableName() {
        return table == null ? null : this.table.getName();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof ClassBasedTableTypeReference) {
            final ClassBasedTableTypeReference that = (ClassBasedTableTypeReference) obj;

            return Objects.equal(getTableName(), that.getTableName());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTableName());
    }
}
