package de.brands4friends.daleq.container;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public final class RowContainer {

    private final List<FieldContainer> fields;

    public RowContainer(final List<FieldContainer> fields) {
        this.fields = ImmutableList.copyOf(Preconditions.checkNotNull(fields));
    }

    public List<FieldContainer> getFields() {
        return fields;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fields);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof RowContainer) {
            final RowContainer that = (RowContainer) obj;

            return Objects.equal(fields, that.fields);
        }

        return false;
    }

    @Override
    public String toString() {
        return "[" + Joiner.on(",").join(fields) + "]";
    }
}
