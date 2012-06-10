package de.brands4friends.daleq.internal.container;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public final class TableContainer {

    private final String name;
    private final List<RowContainer> rows;

    public TableContainer(final String name, final List<RowContainer> rows) {
        this.name = Preconditions.checkNotNull(name);
        this.rows = ImmutableList.copyOf(Preconditions.checkNotNull(rows));
    }

    public String getName() {
        return name;
    }

    public List<RowContainer> getRows() {
        return rows;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TableContainer) {
            final TableContainer that = (TableContainer) obj;

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
