package de.brands4friends.daleq.internal.builder;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.brands4friends.daleq.RowContainer;
import de.brands4friends.daleq.TableContainer;

public final class TableContainerImpl implements TableContainer {

    private final String name;
    private final List<RowContainer> rows;

    public TableContainerImpl(final String name, final List<RowContainer> rows) {
        this.name = Preconditions.checkNotNull(name);
        this.rows = ImmutableList.copyOf(Preconditions.checkNotNull(rows));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<RowContainer> getRows() {
        return rows;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TableContainerImpl) {
            final TableContainerImpl that = (TableContainerImpl) obj;

            return Objects.equal(name, that.name)
                    && Objects.equal(rows, that.rows);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, rows);
    }

    @Override
    public String toString() {
        return "[" + name + ":" + Joiner.on(",").join(rows) + "]";
    }
}
