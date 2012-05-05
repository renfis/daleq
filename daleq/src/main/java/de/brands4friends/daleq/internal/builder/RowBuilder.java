package de.brands4friends.daleq.internal.builder;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.Row;
import de.brands4friends.daleq.internal.container.PropertyContainer;
import de.brands4friends.daleq.internal.container.RowContainer;
import de.brands4friends.daleq.internal.structure.PropertyStructure;
import de.brands4friends.daleq.internal.structure.TableStructure;

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

    public RowContainer build(Context context, TableStructure tableStructure){

        Map<PropertyStructure,PropertyHolder> structureToHolder = Maps.uniqueIndex(properties, new Function<PropertyHolder, PropertyStructure>() {
            @Override
            public PropertyStructure apply(@Nullable final PropertyHolder input) {
                input.getPropertyDef();
                return null;
            }
        });

        final List<PropertyContainer> propertyContainers = Lists.newArrayList();
        for(PropertyStructure propertyStructure : tableStructure.getProperties()){

        }

        RowContainer rowContainer = new RowContainer(tableStructure,propertyContainers);
        return rowContainer;
    }

    public static RowBuilder row(final Object binding){
        return new RowBuilder(binding);
    }
}
