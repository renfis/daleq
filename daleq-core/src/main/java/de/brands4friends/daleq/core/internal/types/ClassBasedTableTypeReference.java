package de.brands4friends.daleq.core.internal.types;

import de.brands4friends.daleq.core.Context;
import de.brands4friends.daleq.core.TableType;
import de.brands4friends.daleq.core.TableTypeReference;

/**
 *
 */
public final class ClassBasedTableTypeReference<T> implements TableTypeReference {

    private final Class<T> table;

    private ClassBasedTableTypeReference(final Class<T> table) {
        this.table = table;
    }

    @Override
    public TableType resolve(final Context context) {
        return toTableType(context);
    }

    private TableType toTableType(final Context context) {
        final TableTypeFactory tableTypeFactory = context.getService(TableTypeFactory.class);
        return tableTypeFactory.create(table);
    }

    public static <T> TableTypeReference of(final Class<T> table) {
        return new ClassBasedTableTypeReference<T>(table);
    }
}
