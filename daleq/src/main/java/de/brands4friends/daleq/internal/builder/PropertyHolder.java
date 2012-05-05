package de.brands4friends.daleq.internal.builder;

import com.google.common.base.Objects;

import de.brands4friends.daleq.PropertyDef;

public class PropertyHolder {
    private final PropertyDef propertyDef;
    private final Object value;

    public PropertyHolder(final PropertyDef propertyDef, final Object value) {
        this.propertyDef = propertyDef;
        this.value = value;
    }

    public PropertyDef getPropertyDef() {
        return propertyDef;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(propertyDef, value);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof PropertyHolder) {
            final PropertyHolder that = (PropertyHolder) obj;

            return Objects.equal(propertyDef, that.propertyDef)
                    && Objects.equal(value, that.value);
        }

        return false;
    }
}
