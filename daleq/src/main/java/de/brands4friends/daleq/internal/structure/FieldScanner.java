package de.brands4friends.daleq.internal.structure;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.internal.template.TemplateValue;

/**
 * Scans classes for PropertyDefs and returns the findings as PropertyStructures
 */
class FieldScanner {

    public <T> List<FieldStructure> scan(final Class<T> fromClass) {

        try {
            final List<FieldStructure> result = Lists.newArrayList();
            for (Field field : fromClass.getDeclaredFields()) {
                if (isConstant(field) && isPropertyDef(field)) {
                    addStructureOfField(result, field);
                }
            }

            if (result.isEmpty()) {
                throw new IllegalArgumentException(
                        "No PropertyType Definitions in class '" + fromClass.getSimpleName() + "'");
            }

            return result;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private void addStructureOfField(
            final List<FieldStructure> result,
            final Field field
    ) throws IllegalAccessException {
        final FieldDef fieldDef = (FieldDef) field.get(null);
        result.add(toFieldStructure(field, fieldDef));
    }

    private FieldStructure toFieldStructure(final Field field, final FieldDef fieldDef) {
        final String name = toName(field, fieldDef);
        final DataType dataType = fieldDef.getDataType();
        final TemplateValue templateValue = fieldDef.getTemplate();
        return new FieldStructure(name, dataType, templateValue, fieldDef);
    }

    private String toName(final Field field, final FieldDef fieldDef) {
        return fieldDef.hasName() ? fieldDef.getName() : field.getName();
    }

    private boolean isPropertyDef(final Field field) {
        return field.getType().isAssignableFrom(FieldDef.class);
    }

    private boolean isConstant(final Field field) {
        final int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers)
                && Modifier.isFinal(modifiers)
                && Modifier.isPublic(modifiers);
    }
}
