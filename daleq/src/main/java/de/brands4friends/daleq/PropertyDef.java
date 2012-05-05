package de.brands4friends.daleq;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Objects;

public class PropertyDef {

    private final String name;
    private final DataType dataType;

    public PropertyDef(final String name, final DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof PropertyDef) {
            final PropertyDef that = (PropertyDef) obj;

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
        return Objects.toStringHelper(this).add("name",name).add("dataType",dataType).toString();
    }

    public static PropertyDef pd(final String name,final DataType dataType){
        return new PropertyDef(name,dataType);
    }
}
