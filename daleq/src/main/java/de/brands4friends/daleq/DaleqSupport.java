package de.brands4friends.daleq;

public interface DaleqSupport {

    void insertIntoDatabase(Table... tables);

    void assertTableInDatabase(Table table);
}
