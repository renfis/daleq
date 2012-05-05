package de.brands4friends.daleq.internal.builder;

import java.util.List;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.Property;
import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.Row;

public class RowBuilder implements Row {

    private final Object binding;
    private final List<Property> properties;

    public RowBuilder(final Object binding, final List<Property> properties) {
        this.binding = binding;
        this.properties = properties;
    }

    public Row with(PropertyDef propertyDef,Object value){
        // TODO
        return null;
    }

    public static RowBuilder row(final Object binding){
        return new RowBuilder(binding, Lists.<Property>newArrayList());
    }
}
