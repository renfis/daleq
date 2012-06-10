package de.brands4friends.daleq.container;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public final class FieldContainerImpl implements FieldContainer {
    private final String name;
    private final String value;

    public FieldContainerImpl(final String name, final String value) {
        this.name = Preconditions.checkNotNull(name);
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof FieldContainerImpl) {
            final FieldContainerImpl that = (FieldContainerImpl) obj;

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
