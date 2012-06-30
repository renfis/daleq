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

package de.brands4friends.daleq.internal.types;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.FieldDef;

/**
 * Scans classes for FieldDefs and returns the findings as FieldTypes
 */
class FieldScanner {

    public <T> List<FieldType> scan(final Class<T> fromClass) {
        final List<FieldType> result = Lists.newArrayList();
        for (Field field : fromClass.getDeclaredFields()) {
            if (isConstant(field) && isFieldDef(field)) {
                result.add(mapFieldToFieldType(field));
            }
        }
        checkResultHasFields(fromClass, result);
        return result;
    }

    private <T> void checkResultHasFields(final Class<T> fromClass, final List<FieldType> result) {
        if (result.isEmpty()) {
            throw new IllegalArgumentException(
                    "No Field Definitions in class '" + fromClass.getSimpleName() + "'");
        }
    }

    private FieldType mapFieldToFieldType(final Field field) {
        try {
            final FieldDef fieldDef = (FieldDef) field.get(null);
            final String name = fieldDef.getName().or(field.getName());
            final DataType dataType = fieldDef.getDataType();
            return new FieldType(name, dataType, fieldDef.getTemplate(), fieldDef);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean isFieldDef(final Field field) {
        return field.getType().isAssignableFrom(FieldDef.class);
    }

    private boolean isConstant(final Field field) {
        final int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers)
                && Modifier.isFinal(modifiers)
                && Modifier.isPublic(modifiers);
    }
}
