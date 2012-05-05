package de.brands4friends.daleq.internal.builder;

import java.util.List;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.Row;

public class RowBuilder implements Row {

    private final Object binding;
    private final List<PropertyHolder> properties;

    public RowBuilder(final Object binding) {
        this.binding = binding;
        this.properties = Lists.newArrayList();
    }

    public Row p(PropertyDef propertyDef, Object value){
        properties.add(new PropertyHolder(propertyDef,value));
        return this;
    }

    public static RowBuilder row(final Object binding){
        return new RowBuilder(binding);
    }
}
