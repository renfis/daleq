package de.brands4friends.daleq;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public final class FieldDef{

    private String name;
    private DataType dataType;

    public FieldDef(final String name, final DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public boolean hasName() {
        return name != null;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    // fluent mutators
    public FieldDef name(final String name){
        this.name = Preconditions.checkNotNull(name);
        return this;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("name", name).add("dataType", dataType).toString();
    }

    public static FieldDef fd(final DataType dataType) {
        return new FieldDef(null, dataType);
    }
}
