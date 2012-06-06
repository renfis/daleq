package de.brands4friends.daleq;

import de.brands4friends.daleq.internal.builder.RowBuilder;
import de.brands4friends.daleq.internal.builder.TableBuilder;

public class Daleq {

    public static <T> Table aTable(Class<T> fromClass){
        return TableBuilder.aTable(fromClass);
    }

    public static Row aRow(long id){
        return RowBuilder.row(id);
    }
}
