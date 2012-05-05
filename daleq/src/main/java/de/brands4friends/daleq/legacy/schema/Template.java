package de.brands4friends.daleq.legacy.schema;

public interface Template {
    <T> Table toTable(String tableName, Iterable<T> generateByIterable, String varName);

    <T> Table toTable(String tableName, Iterable<T> expandOnIterable);

    Row toRow(Object varValue, String varName);

    Row toRow(Object varValue);
}
