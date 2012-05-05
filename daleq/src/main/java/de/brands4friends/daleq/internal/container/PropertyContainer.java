package de.brands4friends.daleq.internal.container;

import com.google.common.base.Objects;

import de.brands4friends.daleq.internal.structure.PropertyStructure;

public class PropertyContainer {
    private final PropertyStructure structure;
    private final Object value;

    public PropertyContainer(final PropertyStructure structure, final Object value) {
        this.structure = structure;
        this.value = value;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(structure, value);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof PropertyContainer) {
            final PropertyContainer that = (PropertyContainer) obj;

            return Objects.equal(structure, that.structure)
                    && Objects.equal(value,that.value);
        }

        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("structure",structure).add("value",value).toString();
    }
}
