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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;

import de.brands4friends.daleq.core.internal.types.TableTypeRepository;

final class TableBuilder implements Table {

    private final TableTypeReference tableRef;
    private final List<Row> rows;

    TableBuilder(final TableTypeReference tableRef) {
        this.tableRef = Preconditions.checkNotNull(tableRef);
        this.rows = Lists.newArrayList();
    }

    @Override
    public Table with(final Row... rows) {
        Preconditions.checkNotNull(rows, "rows should not be null");
        for (final Row row : rows) {
            Preconditions.checkNotNull(row, "each row should not be null");
            this.rows.add(row);
        }
        return this;
    }

    @Override
    public Table withSomeRows(final Iterable<Long> ids) {
        Preconditions.checkNotNull(ids);
        for (Long id : ids) {
            Preconditions.checkNotNull(id, "each id should not be null");
            this.rows.add(Daleq.aRow(id));
        }
        return this;
    }

    @Override
    public Table withSomeRows(final long... ids) {
        return withSomeRows(Longs.asList(ids));
    }

    @Override
    public Table withRowsUntil(final long maxId) {
        return withRowsBetween(0, maxId - 1);
    }

    @Override
    public Table withRowsBetween(final long from, final long to) {
        Preconditions.checkArgument(from >= 0, "Parameter from should be >= 0");
        Preconditions.checkArgument(to >= 0, "Parameter to should be >= 0");
        Preconditions.checkArgument(from <= to,
                "Parameter 'from' should be less than or equal to parameter 'to', but %s<=%s is not true.", from, to);
        for (long i = from; i <= to; i++) {
            this.rows.add(Daleq.aRow(i));
        }
        return this;
    }

    @Override
    public Table allHaving(final FieldTypeReference field, @Nullable final Object value) {
        Preconditions.checkNotNull(field, "field should not be null");
        for (Row row : rows) {
            row.f(field, value);
        }
        return this;
    }

    @Override
    public Table having(final FieldTypeReference field, final Object... values) {
        return havingIterable(field, Arrays.asList(values));
    }

    @Override
    public <T> Table havingFrom(final FieldTypeReference field, final Iterable<T> values) {
        Preconditions.checkNotNull(field);
        Preconditions.checkNotNull(values);
        final Iterator<T> iter = values.iterator();
        for (Row row : rows) {
            if (!iter.hasNext()) {
                return this;
            }
            row.f(field, iter.next());
        }
        return this;
    }

    @Override
    public <T> Table havingIterable(final FieldTypeReference field, final Iterable<Object> values) {
        return havingFrom(field, values);
    }

    @Override
    public TableData build(final Context context) {
        Preconditions.checkNotNull(context, "context should not be null.");
        final TableType tableType = toTableType(context);
        final List<RowData> rows = Lists.transform(this.rows, new Function<Row, RowData>() {
            @Override
            public RowData apply(@Nullable final Row row) {
                if (row == null) {
                    throw new IllegalArgumentException("row");
                }
                return row.build(context, tableType);
            }
        });
        return new ImmutableTableData(tableType, rows);
    }

    private TableType toTableType(final Context context) {
        final TableTypeRepository repository = context.getService(TableTypeRepository.class);
        return repository.get(tableRef);
    }

}
