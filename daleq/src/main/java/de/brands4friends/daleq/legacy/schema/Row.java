package de.brands4friends.daleq.legacy.schema;

import java.util.Collection;

public interface Row {
    Property get(String name);

    Row with(Property prop);

    Collection<Property> getProperties();

    Row duplicate();

    Row add(Property property);

    Row without(String... propertyNames);
}
