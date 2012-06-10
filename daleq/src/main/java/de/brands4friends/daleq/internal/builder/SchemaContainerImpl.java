package de.brands4friends.daleq.internal.builder;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.brands4friends.daleq.SchemaContainer;
import de.brands4friends.daleq.TableContainer;

public final class SchemaContainerImpl implements SchemaContainer {

    private final List<TableContainer> tables;

    public SchemaContainerImpl(final List<TableContainer> tables) {
        this.tables = ImmutableList.copyOf(Preconditions.checkNotNull(tables));
    }

    @Override
    public List<TableContainer> getTables() {
        return tables;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof SchemaContainerImpl) {
            final SchemaContainerImpl that = (SchemaContainerImpl) obj;

            return Objects.equal(tables, that.tables);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tables);
    }
}
