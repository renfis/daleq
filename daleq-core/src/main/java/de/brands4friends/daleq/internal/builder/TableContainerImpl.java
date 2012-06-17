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

package de.brands4friends.daleq.internal.builder;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import de.brands4friends.daleq.RowContainer;
import de.brands4friends.daleq.TableContainer;
import de.brands4friends.daleq.internal.types.TableType;

public final class TableContainerImpl implements TableContainer {

    private final TableType tableType;
    private final List<RowContainer> rows;

    public TableContainerImpl(final TableType tableType, final List<RowContainer> rows) {
        this.tableType = Preconditions.checkNotNull(tableType);
        this.rows = ImmutableList.copyOf(Preconditions.checkNotNull(rows));
    }

    @Override
    public TableType getTableType() {
        return tableType;
    }

    @Override
    public String getName() {
        return tableType.getName();
    }

    @Override
    public List<RowContainer> getRows() {
        return rows;
    }

    @Override
    public Iterable<Optional<String>> getValuesOfField(final String fieldName) {
        return Iterables.transform(rows, new Function<RowContainer, Optional<String>>() {
            @Override
            public Optional<String> apply(@Nullable final RowContainer row) {
                if (row == null) {
                    return null;
                }
                return row.getFieldBy(fieldName).getValue();
            }
        });
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TableContainerImpl) {
            final TableContainerImpl that = (TableContainerImpl) obj;

            return Objects.equal(tableType, that.tableType)
                    && Objects.equal(rows, that.rows);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tableType, rows);
    }

    @Override
    public String toString() {
        return "[" + tableType + ":" + Joiner.on(",").join(rows) + "]";
    }
}
