package de.brands4friends.daleq.legacy.schema;

import de.brands4friends.daleq.legacy.PropertyWriter;

public interface Property {
    String getName();

    Object getValue();

    PropertyType getType();

    void write(PropertyWriter writer);
}
