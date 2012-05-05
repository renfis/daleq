package de.brands4friends.daleq.internal.structure;

import static com.google.common.base.Objects.toStringHelper;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Objects;

public final class PropertyStructure {
    private final String name;
    private final DataType dataType;

    public PropertyStructure(final String name, final DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof PropertyStructure) {
            final PropertyStructure that = (PropertyStructure) obj;

            return Objects.equal(name, that.name)
                    && Objects.equal(dataType, that.dataType);
        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(name, dataType);
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("name",name).add("dataType",dataType).toString();
    }
}
