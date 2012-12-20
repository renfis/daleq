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

package de.brands4friends.daleq.core;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.core.internal.conversion.TypeConversion;
import de.brands4friends.daleq.core.internal.template.TemplateValueFactory;

class RowBuilder implements Row {

    private final long binding;
    private final Map<FieldTypeReference, FieldHolder> fields;

    RowBuilder(final long binding) {
        this.binding = binding;
        this.fields = Maps.newHashMap();
    }

    @Override
    public Row f(final FieldTypeReference fieldTypeReference, @Nullable final Object value) {
        Preconditions.checkNotNull(fieldTypeReference);
        fields.put(fieldTypeReference, new FieldHolder(fieldTypeReference, value));
        return this;
    }

    @Override
    public RowData build(final Context context, final TableType tableType) {
        final Map<FieldType, FieldHolder> typeToHolderIndex = createTypeToHolderIndex(tableType);

        final List<FieldData> fields =
                mapFieldsToContainers(context, tableType, typeToHolderIndex);

        return new ImmutableRowData(fields);
    }

    private List<FieldData> mapFieldsToContainers(
            final Context context,
            final TableType tableType,
            final Map<FieldType, FieldHolder> typeToHolder) {

        return Lists.transform(tableType.getFields(), new Function<FieldType, FieldData>() {
            @Override
            public FieldData apply(@Nullable final FieldType fieldType) {
                final FieldHolder actualField = typeToHolder.get(fieldType);
                if (actualField == null) {
                    return convertDefaultField(fieldType, context);
                }
                return convertProvidedField(fieldType, actualField, context);
            }
        });
    }

    private FieldData convertDefaultField(final FieldType fieldType, final Context context) {
        final TemplateValue templateValue = toTemplate(fieldType, context);

        final String renderedValue = templateValue.render(binding);
        return new ImmutableFieldData(fieldType.getName(), renderedValue);
    }

    private TemplateValue toTemplate(final FieldType fieldType, final Context context) {
        if (fieldType.getTemplateValue().isPresent()) {
            return fieldType.getTemplateValue().get();
        } else {
            final TemplateValueFactory factory = context.getService(TemplateValueFactory.class);
            return factory.create(fieldType.getDataType(), fieldType.getName());
        }
    }

    private FieldData convertProvidedField(
            final FieldType fieldType,
            final FieldHolder actualField,
            final Context context) {
        final String strValue = convert(context, actualField.getValue());
        return new ImmutableFieldData(fieldType.getName(), strValue);
    }

    private String convert(final Context context, final Object valueToConvert) {
        final TypeConversion typeConversion = context.getService(TypeConversion.class);
        return typeConversion.convert(valueToConvert);
    }

    private Map<FieldType, FieldHolder> createTypeToHolderIndex(final TableType tableType) {
        return Maps.uniqueIndex(fields.values(), new Function<FieldHolder, FieldType>() {
            @Override
            public FieldType apply(@Nullable final FieldHolder fieldHolder) {
                if (fieldHolder == null) {
                    throw new IllegalArgumentException("fieldHolder");
                }
                final FieldTypeReference fieldTypeReference = fieldHolder.getFieldTypeRef();
                final FieldType fieldType = fieldTypeReference.resolve(tableType);
                if (fieldType == null) {
                    return throwNoSuchDaleqFieldException(fieldHolder, tableType);
                }
                return fieldType;
            }
        });
    }

    private FieldType throwNoSuchDaleqFieldException(final FieldHolder fieldHolder, final TableType tableType) {
        final String msg = String.format(
                "The row contains a field '%s', " +
                        "but the table '%s' does not contain such a field definition.",
                fieldHolder.getFieldTypeRef(),
                tableType.getName()
        );
        throw new NoSuchDaleqFieldException(msg);
    }

}
