package de.brands4friends.daleq.internal.container;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public final class SchemaContainer {

    private final List<TableContainer> tables;

    public SchemaContainer(final List<TableContainer> tables) {
        this.tables = ImmutableList.copyOf(Preconditions.checkNotNull(tables));
    }

    public List<TableContainer> getTables() {
        return tables;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SchemaContainer) {
            final SchemaContainer that = (SchemaContainer) obj;

            return Objects.equal(tables, that.tables);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tables);
    }
}
