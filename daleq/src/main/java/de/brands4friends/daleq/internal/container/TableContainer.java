package de.brands4friends.daleq.internal.container;

import java.util.List;

import com.google.common.base.Objects;

import de.brands4friends.daleq.internal.structure.TableStructure;

public class TableContainer {
    
    private final TableStructure tableStructure;
    private final List<RowContainer> rows;

    public TableContainer(final TableStructure tableStructure, final List<RowContainer> rows) {
        this.tableStructure = tableStructure;
        this.rows = rows;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof TableContainer) {
            final TableContainer that = (TableContainer) obj;

            return Objects.equal(tableStructure, that.tableStructure)
                    && Objects.equal(rows,that.rows);
        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(tableStructure, rows);
    }
}
