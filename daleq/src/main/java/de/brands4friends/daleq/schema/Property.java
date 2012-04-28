package de.brands4friends.daleq.schema;

import de.brands4friends.daleq.PropertyWriter;

public interface Property {
    String getName();

    Object getValue();

    PropertyType getType();

    void write(PropertyWriter writer);
}
