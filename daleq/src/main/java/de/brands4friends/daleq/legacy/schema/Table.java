package de.brands4friends.daleq.legacy.schema;

import java.util.Iterator;
import java.util.List;

public interface Table extends Iterable<Row> {
    String getName();

    List<Row> getRows();

    Table add(Property prop);

    Table join(Table otherList, PropertyType onOtherProperty, PropertyType asThisProperty);

    <T> Table add(PropertyType propertyType, Iterable<T> values);

    Iterator<Row> iterator();

    Table concat(Table... tables);

    Table concat(Row... rows);

    Table without(String... propertyNames);

    Table duplicate();
}
