package de.brands4friends.daleq.container;

import java.util.List;

public interface TableContainer {
    String getName();

    List<RowContainer> getRows();
}
