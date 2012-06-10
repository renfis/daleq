package de.brands4friends.daleq.container;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public final class RowContainerImpl implements RowContainer {

    private final List<FieldContainer> fields;

    public RowContainerImpl(final List<FieldContainer> fields) {
        this.fields = ImmutableList.copyOf(Preconditions.checkNotNull(fields));
    }

    @Override
    public List<FieldContainer> getFields() {
        return fields;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fields);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof RowContainerImpl) {
            final RowContainerImpl that = (RowContainerImpl) obj;

            return Objects.equal(fields, that.fields);
        }

        return false;
    }

    @Override
    public String toString() {
        return "[" + Joiner.on(",").join(fields) + "]";
    }
}
