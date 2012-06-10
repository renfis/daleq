package de.brands4friends.daleq.internal.builder;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.brands4friends.daleq.FieldDef;

public class FieldHolder {
    private final FieldDef fieldDef;
    private final Object value;

    public FieldHolder(final FieldDef fieldDef, final Object value) {
        this.fieldDef = Preconditions.checkNotNull(fieldDef);
        this.value = value;
    }

    public FieldDef getFieldDef() {
        return fieldDef;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(fieldDef, value);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof FieldHolder) {
            final FieldHolder that = (FieldHolder) obj;

            return Objects.equal(fieldDef, that.fieldDef)
                    && Objects.equal(value, that.value);
        }

        return false;
    }
}
