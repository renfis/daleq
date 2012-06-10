package de.brands4friends.daleq.internal.types;

import java.util.List;

import com.google.common.base.Objects;

public class SchemaType {

    private final List<TableType> tables;

    public SchemaType(final List<TableType> tables) {
        this.tables = tables;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof SchemaType) {
            final SchemaType that = (SchemaType) obj;

            return Objects.equal(tables, that.tables);
        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(tables);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("tables", tables).toString();
    }
}
