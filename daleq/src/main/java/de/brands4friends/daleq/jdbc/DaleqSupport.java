package de.brands4friends.daleq.jdbc;

import de.brands4friends.daleq.Table;

public interface DaleqSupport {

    void insertIntoDatabase(Table... tables);
    void assertTableInDatabase(Table table);
}
