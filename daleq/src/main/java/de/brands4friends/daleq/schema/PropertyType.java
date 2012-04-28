package de.brands4friends.daleq.schema;

import static com.google.common.base.Objects.equal;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Objects;

public class PropertyType {

    private final String name;
    // TODO use DbUnit's types
    private final DataType dataType;

    public PropertyType(final String name, final DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Property of(Object value){
        return Daleq.p(this, value);
    }

    @Override
    public final boolean equals(Object obj) {
        if(obj instanceof PropertyType) {
            final PropertyType that = (PropertyType) obj;

            return equal(name, that.name) && equal(dataType, that.dataType);
        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(name,dataType);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("name",name).add("dataType",dataType).toString();
    }
}
