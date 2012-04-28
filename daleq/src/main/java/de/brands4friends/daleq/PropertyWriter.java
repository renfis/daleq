package de.brands4friends.daleq;

public interface PropertyWriter {

    void writePlain(final String name,final String value);
    void writeMapped(final String name, final Object value);
}
