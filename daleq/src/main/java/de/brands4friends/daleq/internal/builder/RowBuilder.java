package de.brands4friends.daleq.internal.builder;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.DaleqBuildException;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.Row;
import de.brands4friends.daleq.container.FieldContainer;
import de.brands4friends.daleq.container.RowContainer;
import de.brands4friends.daleq.internal.structure.FieldStructure;
import de.brands4friends.daleq.internal.structure.TableStructure;
import de.brands4friends.daleq.internal.template.TemplateValue;
import de.brands4friends.daleq.internal.template.TemplateValueDefaultProvider;

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

        return new RowContainer(fieldContainers);
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
                    return convertDefaultField(fieldStructure, context);
                }
                return convertProvidedField(fieldStructure, actualField, context);
            }
        });
    }

    private FieldContainer convertDefaultField(final FieldStructure fieldStructure, final Context context) {
        // apply template binding to template
        final TemplateValue templateValue = toTemplate(fieldStructure, context);

        final String renderedValue = templateValue.render(binding);
        return new FieldContainer(fieldStructure.getName(), renderedValue);
    }

    private TemplateValue toTemplate(final FieldStructure fieldStructure, final Context context) {
        if (fieldStructure.hasTemplateValue()) {
            return fieldStructure.getTemplateValue();
        } else {
            final TemplateValueDefaultProvider defaultProvider = context.getTemplateValueDefaultProvider();
            return defaultProvider.toDefault(fieldStructure.getDataType(), fieldStructure.getName());
        }
    }

    private FieldContainer convertProvidedField(
            final FieldStructure fieldStructure,
            final FieldHolder actualField,
            final Context context) {
        final String strValue = convert(context, actualField.getValue());
        return new FieldContainer(fieldStructure.getName(), strValue);
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
                            "The row contains a field '%s', " +
                                    "but the table '%s' does not contain such a field definition.",
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
