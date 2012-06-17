package de.brands4friends.daleq.internal.types;

public interface TableTypeFactory {
    <T> TableType create(Class<T> fromClass);
}
