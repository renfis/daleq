package de.brands4friends.daleq.internal.builder;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.Context;
import de.brands4friends.daleq.DaleqBuildException;
import de.brands4friends.daleq.FieldContainer;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.Row;
import de.brands4friends.daleq.RowContainer;
import de.brands4friends.daleq.internal.template.TemplateValue;
import de.brands4friends.daleq.internal.template.TemplateValueFactory;
import de.brands4friends.daleq.internal.types.FieldType;
import de.brands4friends.daleq.internal.types.TableType;

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
    public RowContainer build(final Context context, final TableType tableType) {
        final Map<FieldType, FieldHolder> structureToHolder = createStructureToHolderIndex(tableType);

        final List<FieldContainer> fieldContainers =
                mapPropertiesToContainers(context, tableType, structureToHolder);

        return new RowContainerImpl(fieldContainers);
    }

    private List<FieldContainer> mapPropertiesToContainers(
            final Context context,
            final TableType tableType,
            final Map<FieldType, FieldHolder> structureToHolder) {

        return Lists.transform(tableType.getFields(), new Function<FieldType, FieldContainer>() {
            @Override
            public FieldContainer apply(final FieldType fieldType) {
                final FieldHolder actualField = structureToHolder.get(fieldType);
                if (actualField == null) {
                    return convertDefaultField(fieldType, context);
                }
                return convertProvidedField(fieldType, actualField, context);
            }
        });
    }

    private FieldContainer convertDefaultField(final FieldType fieldType, final Context context) {
        // apply template binding to template
        final TemplateValue templateValue = toTemplate(fieldType, context);

        final String renderedValue = templateValue.render(binding);
        return new FieldContainerImpl(fieldType.getName(), renderedValue);
    }

    private TemplateValue toTemplate(final FieldType fieldType, final Context context) {
        if (fieldType.hasTemplateValue()) {
            return fieldType.getTemplateValue();
        } else {
            final TemplateValueFactory factory = context.getTemplateValueFactory();
            return factory.create(fieldType.getDataType(), fieldType.getName());
        }
    }

    private FieldContainer convertProvidedField(
            final FieldType fieldType,
            final FieldHolder actualField,
            final Context context) {
        final String strValue = convert(context, actualField.getValue());
        return new FieldContainerImpl(fieldType.getName(), strValue);
    }

    private String convert(final Context context, final Object valueToConvert) {
        return context.getTypeConversion().convert(valueToConvert);
    }

    private Map<FieldType, FieldHolder> createStructureToHolderIndex(final TableType tableType) {
        return Maps.uniqueIndex(fields, new Function<FieldHolder, FieldType>() {
            @Override
            public FieldType apply(final FieldHolder fieldHolder) {
                final FieldType fieldType = tableType.findStructureByDef(fieldHolder.getFieldDef());
                if (fieldType == null) {
                    final String msg = String.format(
                            "The row contains a field '%s', " +
                                    "but the table '%s' does not contain such a field definition.",
                            fieldHolder.getFieldDef(),
                            tableType.getName()
                    );
                    throw new DaleqBuildException(msg);
                }
                return fieldType;
            }
        });
    }

    public static RowBuilder aRow(final long binding) {
        return new RowBuilder(binding);
    }
}
