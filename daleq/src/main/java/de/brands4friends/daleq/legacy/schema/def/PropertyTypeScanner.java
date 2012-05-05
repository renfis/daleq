package de.brands4friends.daleq.legacy.schema.def;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

import de.brands4friends.daleq.legacy.schema.PropertyType;

public class PropertyTypeScanner {

    public <T> Collection<PropertyType> scan(Class<T> fromClass)  {

        try {
            final Set<PropertyType> result = Sets.newHashSet();
            for(Field field : fromClass.getDeclaredFields()){
                if(isConstant(field) && isPropertyType(field)){
                    result.add((PropertyType) field.get(null));
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

    private boolean isPropertyType(final Field field) {
        return field.getType().isAssignableFrom(PropertyType.class);
    }

    private boolean isConstant(final Field field) {
        final int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers)
           && Modifier.isFinal(modifiers)
           && Modifier.isPublic(modifiers);
    }

}
