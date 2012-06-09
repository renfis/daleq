package de.brands4friends.daleq;

import de.brands4friends.daleq.internal.builder.RowBuilder;
import de.brands4friends.daleq.internal.builder.TableBuilder;

public final class Daleq {

    private Daleq(){

    }


    public static <T> Table aTable(final Class<T> fromClass){
        return TableBuilder.aTable(fromClass);
    }

    public static Row aRow(final long id){
        return RowBuilder.aRow(id);
    }
}
