package de.brands4friends.daleq.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.internal.structure.PropertyStructure;

/**
 * Scans classes for PropertyDefs and returns the findings as PropertyStructures
 */
public class PropertyScanner {

    public <T> Collection<PropertyStructure> scan(Class<T> fromClass)  {

        try {
            final List<PropertyStructure> result = Lists.newArrayList();
            for(Field field : fromClass.getDeclaredFields()){
                if(isConstant(field) && isPropertyDef(field)){
                    addStructureOfField(result, field);
                }
            }

            if(result.isEmpty()){
                throw new IllegalArgumentException(
                        "No PropertyType Definitions in class '" + fromClass.getSimpleName() + "'");
            }

            return result;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private void addStructureOfField(final List<PropertyStructure> result, final Field field) throws IllegalAccessException {
        final PropertyDef propertyDef = (PropertyDef) field.get(null);
        final String name = propertyDef.hasName() ? propertyDef.getName() : field.getName();
        final DataType dataType = propertyDef.getDataType();
        result.add(new PropertyStructure(name, dataType));
    }

    private boolean isPropertyDef(final Field field) {
        return field.getType().isAssignableFrom(PropertyDef.class);
    }

    private boolean isConstant(final Field field) {
        final int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers)
           && Modifier.isFinal(modifiers)
           && Modifier.isPublic(modifiers);
    }
}
