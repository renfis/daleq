package de.brands4friends.daleq.internal.container;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public final class FieldContainer {
    private final String name;
    private final String value;

    public FieldContainer(final String name, final String value) {
        this.name = Preconditions.checkNotNull(name);
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof FieldContainer) {
            final FieldContainer that = (FieldContainer) obj;

            return Objects.equal(name, that.name)
                    && Objects.equal(value, that.value);
        }

        return false;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
