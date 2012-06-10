package de.brands4friends.daleq;

import com.sun.istack.internal.NotNull;

import de.brands4friends.daleq.internal.builder.RowBuilder;
import de.brands4friends.daleq.internal.builder.TableBuilder;

public final class Daleq {

    private Daleq() {

    }


    public static <T> Table aTable(@NotNull final Class<T> fromClass) {
        return TableBuilder.aTable(fromClass);
    }

    public static Row aRow(final long id) {
        return RowBuilder.aRow(id);
    }
}
