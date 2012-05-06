package de.brands4friends.daleq;

import de.brands4friends.daleq.internal.builder.Context;
import de.brands4friends.daleq.internal.container.TableContainer;

public interface Table {

    Table with(Row ... rows);
    Table withSomeRows(Iterable<Object> substitutes);
    TableContainer build(final Context context);

}
