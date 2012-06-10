package de.brands4friends.daleq.internal.builder;

import javax.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import de.brands4friends.daleq.FieldContainer;

public final class FieldContainerImpl implements FieldContainer {
    private final String name;
    private final Optional<String> value;

    public FieldContainerImpl(final String name, @Nullable final String value) {
        this.name = Preconditions.checkNotNull(name);
        this.value = Optional.fromNullable(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getValue() {
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
