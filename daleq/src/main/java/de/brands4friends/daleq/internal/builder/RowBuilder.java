package de.brands4friends.daleq.internal.builder;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.DaleqBuildException;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.Row;
import de.brands4friends.daleq.internal.container.FieldContainer;
import de.brands4friends.daleq.internal.container.RowContainer;
import de.brands4friends.daleq.internal.structure.FieldStructure;
import de.brands4friends.daleq.internal.structure.TableStructure;
import de.brands4friends.daleq.internal.structure.TemplateValue;

public class RowBuilder implements Row {

    private final long binding;
    private final List<FieldHolder> fields;

    public RowBuilder(final long binding) {
        this.binding = binding;
        this.fields = Lists.newArrayList();
    }

    public Row f(final FieldDef fieldDef, final Object value) {
        fields.add(new FieldHolder(fieldDef, value));
        return this;
    }

    @Override
    public RowContainer build(final Context context, final TableStructure tableStructure) {
        final Map<FieldStructure, FieldHolder> structureToHolder = createStructureToHolderIndex(tableStructure);

        final List<FieldContainer> fieldContainers =
                mapPropertiesToContainers(context, tableStructure, structureToHolder);

        return new RowContainer(tableStructure, fieldContainers);
    }

    private List<FieldContainer> mapPropertiesToContainers(
            final Context context,
            final TableStructure tableStructure,
            final Map<FieldStructure, FieldHolder> structureToHolder) {

        return Lists.transform(tableStructure.getFields(), new Function<FieldStructure, FieldContainer>() {
            @Override
            public FieldContainer apply(final FieldStructure fieldStructure) {
                final FieldHolder actualField = structureToHolder.get(fieldStructure);
                if (actualField == null) {
                    return convertDefaultProperty(fieldStructure, context);
                }
                return convertProvidedProperty(fieldStructure, actualField, context);
            }
        });
    }

    private FieldContainer convertDefaultProperty(final FieldStructure fieldStructure, final Context context) {
        // apply template binding to template
        final String coercedBinding = convert(context,binding);
        final TemplateValue templateValue = fieldStructure.getTemplateValue();
        final String renderedValue = templateValue.render(coercedBinding);
        return new FieldContainer(fieldStructure,renderedValue);
    }

    private FieldContainer convertProvidedProperty(
            final FieldStructure fieldStructure,
            final FieldHolder actualField,
            final Context context) {
        final String strValue = convert(context, actualField.getValue());
        return new FieldContainer(fieldStructure, strValue);
    }

    private String convert(final Context context, final Object valueToConvert) {
        return context.getTypeConversion().convert(valueToConvert);
    }

    private Map<FieldStructure, FieldHolder> createStructureToHolderIndex(final TableStructure tableStructure) {
        return Maps.uniqueIndex(fields, new Function<FieldHolder, FieldStructure>() {
            @Override
            public FieldStructure apply(final FieldHolder fieldHolder) {
                final FieldStructure fieldStructure = tableStructure.findStructureByDef(fieldHolder.getFieldDef());
                if (fieldStructure == null) {
                    final String msg = String.format(
                            "The row contains a property '%s', " +
                                    "but the table '%s' does not contain such a property definition.",
                            fieldHolder.getFieldDef(),
                            tableStructure.getName()
                    );
                    throw new DaleqBuildException(msg);
                }
                return fieldStructure;
            }
        });
    }

    public static RowBuilder aRow(final long binding) {
        return new RowBuilder(binding);
    }
}
