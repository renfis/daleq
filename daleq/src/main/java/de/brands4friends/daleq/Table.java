package de.brands4friends.daleq;

import de.brands4friends.daleq.internal.builder.Context;
import de.brands4friends.daleq.internal.container.TableContainer;

public interface Table {

    Table with(Row ... rows);
    Table withSomeRows(Iterable<Long> ids);
    Table withSomeRows(long ... ids);
    TableContainer build(final Context context);

}
