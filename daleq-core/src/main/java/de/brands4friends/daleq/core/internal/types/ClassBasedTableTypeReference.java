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
