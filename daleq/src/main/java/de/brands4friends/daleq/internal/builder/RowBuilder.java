package de.brands4friends.daleq.internal.builder;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.DaleqBuildException;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.Row;
import de.brands4friends.daleq.internal.container.PropertyContainer;
import de.brands4friends.daleq.internal.container.RowContainer;
import de.brands4friends.daleq.internal.structure.PropertyStructure;
import de.brands4friends.daleq.internal.structure.TableStructure;
import de.brands4friends.daleq.internal.structure.TemplateValue;

public class RowBuilder implements Row {

    private final long binding;
    private final List<FieldHolder> fields;

    public RowBuilder(final long binding) {
        this.binding = binding;
        this.fields = Lists.newArrayList();
    }

    public Row p(FieldDef fieldDef, Object value) {
        fields.add(new FieldHolder(fieldDef, value));
        return this;
    }

    @Override
    public RowContainer build(final Context context, final TableStructure tableStructure) {
        final Map<PropertyStructure, FieldHolder> structureToHolder = createStructureToHolderIndex(tableStructure);
        final List<PropertyContainer> propertyContainers = mapPropertiesToContainers(context, tableStructure, structureToHolder);
        return new RowContainer(tableStructure, propertyContainers);
    }

    private List<PropertyContainer> mapPropertiesToContainers(
            final Context context,
            final TableStructure tableStructure,
            final Map<PropertyStructure, FieldHolder> structureToHolder) {

        return Lists.transform(tableStructure.getProperties(), new Function<PropertyStructure, PropertyContainer>() {
            @Override
            public PropertyContainer apply(final PropertyStructure propertyStructure) {
                final FieldHolder actualField = structureToHolder.get(propertyStructure);
                if (actualField != null) {
                    return convertProvidedProperty(propertyStructure, actualField, context);
                } else {
                    return convertDefaultProperty(propertyStructure, context);
                }
            }
        });
    }

    private PropertyContainer convertDefaultProperty(final PropertyStructure propertyStructure, final Context context) {
        // apply template binding to template
        final String coercedBinding = convert(context,binding);
        final TemplateValue templateValue = propertyStructure.getTemplateValue();
        final String renderedValue = templateValue.render(coercedBinding);
        return new PropertyContainer(propertyStructure,renderedValue);
    }

    private PropertyContainer convertProvidedProperty(final PropertyStructure propertyStructure, final FieldHolder actualField, final Context context) {
        final String strValue = convert(context, actualField.getValue());
        return new PropertyContainer(propertyStructure, strValue);
    }

    private String convert(final Context context, final Object valueToConvert) {
        return context.getTypeConversion().convert(valueToConvert);
    }

    private Map<PropertyStructure, FieldHolder> createStructureToHolderIndex(final TableStructure tableStructure) {
        return Maps.uniqueIndex(fields, new Function<FieldHolder, PropertyStructure>() {
            @Override
            public PropertyStructure apply(final FieldHolder fieldHolder) {
                final PropertyStructure propertyStructure = tableStructure.findStructureByDef(fieldHolder.getFieldDef());
                if (propertyStructure == null) {
                    final String msg = String.format(
                            "The row contains a property '%s', but the table '%s' does not contain such a property definition.",
                            fieldHolder.getFieldDef(),
                            tableStructure.getName()
                    );
                    throw new DaleqBuildException(msg);
                }
                return propertyStructure;
            }
        });
    }

    public static RowBuilder row(final long binding) {
        return new RowBuilder(binding);
    }
}
