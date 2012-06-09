package de.brands4friends.daleq;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.brands4friends.daleq.internal.structure.TemplateValue;

public final class FieldDef {

    private String name;
    private final DataType dataType;
    private TemplateValue template;

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

    public TemplateValue getTemplate() {
        return template;
    }

    public boolean hasTemplate() {
        return template != null;
    }

    // fluent mutators
    public FieldDef name(final String name) {
        this.name = Preconditions.checkNotNull(name);
        return this;
    }

    public FieldDef template(final String template) {
        this.template = new TemplateValue(template);
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
