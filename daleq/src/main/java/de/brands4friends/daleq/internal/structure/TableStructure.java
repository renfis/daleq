package de.brands4friends.daleq.internal.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.FieldDef;

public class TableStructure {

    private final String name;
    private final List<PropertyStructure> properties;
    private final Map<FieldDef,PropertyStructure> lookupByDef;

    public TableStructure(final String name, final List<PropertyStructure> properties) {
        this.name = name;
        this.properties = properties;
        this.lookupByDef = Maps.uniqueIndex(properties,new Function<PropertyStructure, FieldDef>() {
            @Override
            public FieldDef apply(final PropertyStructure input) {
                return input.getOrigin();
            }
        });
    }

    public TableStructure(final String name, final PropertyStructure ... properties){
        this(name, Arrays.asList(properties));
    }

    public String getName() {
        return name;
    }

    public List<PropertyStructure> getProperties() {
        return properties;
    }

    public PropertyStructure findStructureByDef(FieldDef fieldDef){
        return lookupByDef.get(fieldDef);
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
