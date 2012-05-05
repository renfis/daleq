package de.brands4friends.daleq.internal.structure;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Objects;

public class TableStructure {

    private final String name;
    private final List<PropertyStructure> properties;

    public TableStructure(final String name, final List<PropertyStructure> properties) {
        this.name = name;
        this.properties = properties;
    }

    public TableStructure(final String name, final PropertyStructure ... properties){
        this(name, Arrays.asList(properties));
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof TableStructure) {
            final TableStructure that = (TableStructure) obj;

            return Objects.equal(name,that.name)
                    && Objects.equal(properties, that.properties);
        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(name,properties);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name",name)
                .add("properties",properties).toString();
    }
}
