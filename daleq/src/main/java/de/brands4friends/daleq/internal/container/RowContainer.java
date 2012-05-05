package de.brands4friends.daleq.internal.container;

import java.util.List;

import com.google.common.base.Objects;

import de.brands4friends.daleq.internal.structure.TableStructure;

public class RowContainer {

    private final TableStructure structure;
    private final List<PropertyContainer> properties;

    public RowContainer(final TableStructure structure, final List<PropertyContainer> properties) {
        this.structure = structure;
        this.properties = properties;
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
        return Objects.toStringHelper(this).add("structure", structure).add("properties", properties).toString();
    }
}
