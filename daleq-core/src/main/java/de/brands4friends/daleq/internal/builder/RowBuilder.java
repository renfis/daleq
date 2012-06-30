/*
 * Copyright 2012 brands4friends, Private Sale GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.brands4friends.daleq.internal.builder;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.Context;
import de.brands4friends.daleq.DaleqBuildException;
import de.brands4friends.daleq.FieldContainer;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.Row;
import de.brands4friends.daleq.RowContainer;
import de.brands4friends.daleq.TemplateValue;
import de.brands4friends.daleq.TemplateValueFactory;
import de.brands4friends.daleq.internal.types.FieldType;
import de.brands4friends.daleq.internal.types.TableType;

public class RowBuilder implements Row {

    private final long binding;
    private final Map<FieldDef, FieldHolder> fields;

    public RowBuilder(final long binding) {
        this.binding = binding;
        this.fields = Maps.newHashMap();
    }

    @Override
    public Row f(final FieldDef fieldDef, @Nullable final Object value) {
        fields.put(fieldDef, new FieldHolder(fieldDef, value));
        return this;
    }

    @Override
    public RowContainer build(final Context context, final TableType tableType) {
        final Map<FieldType, FieldHolder> structureToHolder = createTypeToHolderIndex(tableType);

        final List<FieldContainer> fieldContainers =
                mapFieldsToContainers(context, tableType, structureToHolder);

        return new RowContainerImpl(fieldContainers);
    }

    private List<FieldContainer> mapFieldsToContainers(
            final Context context,
            final TableType tableType,
            final Map<FieldType, FieldHolder> typeToHolder) {

        return Lists.transform(tableType.getFields(), new Function<FieldType, FieldContainer>() {
            @Override
            public FieldContainer apply(final FieldType fieldType) {
                final FieldHolder actualField = typeToHolder.get(fieldType);
                if (actualField == null) {
                    return convertDefaultField(fieldType, context);
                }
                return convertProvidedField(fieldType, actualField, context);
            }
        });
    }

    private FieldContainer convertDefaultField(final FieldType fieldType, final Context context) {
        final TemplateValue templateValue = toTemplate(fieldType, context);

        final String renderedValue = templateValue.render(binding);
        return new FieldContainerImpl(fieldType.getName(), renderedValue);
    }

    private TemplateValue toTemplate(final FieldType fieldType, final Context context) {
        if (fieldType.getTemplateValue().isPresent()) {
            return fieldType.getTemplateValue().get();
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

    private Map<FieldType, FieldHolder> createTypeToHolderIndex(final TableType tableType) {
        return Maps.uniqueIndex(fields.values(), new Function<FieldHolder, FieldType>() {
            @Override
            public FieldType apply(final FieldHolder fieldHolder) {
                final FieldType fieldType = tableType.findFieldBy(fieldHolder.getFieldDef());
                if (fieldType == null) {
                    return throwUnknownFieldException(fieldHolder, tableType);
                }
                return fieldType;
            }
        });
    }

    private FieldType throwUnknownFieldException(final FieldHolder fieldHolder, final TableType tableType) {
        final String msg = String.format(
                "The row contains a field '%s', " +
                        "but the table '%s' does not contain such a field definition.",
                fieldHolder.getFieldDef(),
                tableType.getName()
        );
        throw new DaleqBuildException(msg);
    }

    public static RowBuilder aRow(final long binding) {
        return new RowBuilder(binding);
    }
}
