package de.brands4friends.daleq.internal.container;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.brands4friends.daleq.internal.structure.FieldStructure;

public final class FieldContainer{
    private final FieldStructure structure;
    private final String value;

    public FieldContainer(final FieldStructure structure, final String value) {
        this.structure = Preconditions.checkNotNull(structure);
        this.value     = value;
    }

    public String getName(){
        return structure.getName();
    }

    public FieldStructure getStructure() {
        return structure;
    }

    public String getValue() {
        return value;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(structure, value);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof FieldContainer) {
            final FieldContainer that = (FieldContainer) obj;

            return Objects.equal(structure, that.structure)
                    && Objects.equal(value,that.value);
        }

        return false;
    }

    @Override
    public String toString() {
        return structure.getName() + "=" + value;
    }
}
