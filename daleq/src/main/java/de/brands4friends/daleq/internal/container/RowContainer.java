package de.brands4friends.daleq.internal.container;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.brands4friends.daleq.internal.structure.TableStructure;

public final class RowContainer {

    private final TableStructure structure;
    private final List<PropertyContainer> properties;

    public RowContainer(final TableStructure structure, final List<PropertyContainer> properties) {
        this.structure  = Preconditions.checkNotNull(structure);
        this.properties = Preconditions.checkNotNull(properties);
    }

    public List<PropertyContainer> getProperties() {
        return properties;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(structure, properties);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof RowContainer) {
            final RowContainer that = (RowContainer) obj;

            return Objects.equal(structure, that.structure)
                    && Objects.equal(properties, that.properties);
        }

        return false;
    }

    @Override
    public String toString() {
        return "[" + Joiner.on(",").join(properties) + "]";
    }
}
