package de.brands4friends.daleq.internal.structure;

import java.util.List;

import com.google.common.base.Objects;

public class SchemaStructure {

    private final List<TableStructure> tables;

    public SchemaStructure(final List<TableStructure> tables) {
        this.tables = tables;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof SchemaStructure) {
            final SchemaStructure that = (SchemaStructure) obj;

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
        return Objects.toStringHelper(this).add("tables",tables).toString();
    }
}
