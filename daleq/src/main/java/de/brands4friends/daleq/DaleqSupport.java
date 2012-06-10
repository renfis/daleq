package de.brands4friends.daleq;

import com.sun.istack.internal.NotNull;

public interface DaleqSupport {

    void insertIntoDatabase(@NotNull Table... tables);

    void assertTableInDatabase(@NotNull Table table);
}
