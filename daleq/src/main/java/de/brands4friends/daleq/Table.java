package de.brands4friends.daleq;

import com.sun.istack.internal.NotNull;

public interface Table {

    Table with(@NotNull Row... rows);

    Table withSomeRows(@NotNull Iterable<Long> ids);

    Table withSomeRows(@NotNull long... ids);

    Table withRowsUntil(long maxId);

    TableContainer build(@NotNull final Context context);

}
