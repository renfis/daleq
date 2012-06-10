package de.brands4friends.daleq;

import de.brands4friends.daleq.container.TableContainer;
import de.brands4friends.daleq.internal.builder.Context;

public interface Table {

    Table with(Row... rows);

    Table withSomeRows(Iterable<Long> ids);

    Table withSomeRows(long... ids);

    Table withRowsUntil(long maxId);

    TableContainer build(final Context context);

}
