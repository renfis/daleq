package de.brands4friends.daleq.internal.container;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.brands4friends.daleq.internal.structure.TableStructure;

public final class TableContainer {
    
    private final TableStructure tableStructure;
    private final List<RowContainer> rows;

    public TableContainer(final TableStructure tableStructure, final List<RowContainer> rows) {
        this.tableStructure = Preconditions.checkNotNull(tableStructure);
        this.rows = ImmutableList.copyOf(Preconditions.checkNotNull(rows));
    }

    public String getName(){
        return tableStructure.getName();
    }

    public List<RowContainer> getRows() {
        return rows;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TableContainer) {
            final TableContainer that = (TableContainer) obj;

            return Objects.equal(tableStructure, that.tableStructure)
                    && Objects.equal(rows,that.rows);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tableStructure, rows);
    }

    @Override
    public String toString() {
        return "[" + tableStructure.getName() + ":" + Joiner.on(",").join(rows) + "]";
    }
}
