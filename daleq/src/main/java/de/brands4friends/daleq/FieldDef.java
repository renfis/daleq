package de.brands4friends.daleq;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.brands4friends.daleq.internal.template.StringTemplateValue;

public final class FieldDef {

    private String name;
    private final DataType dataType;
    private final TemplateValue template;

    public FieldDef(final String name, final DataType dataType, final TemplateValue template) {
        this.name = name;
        this.dataType = Preconditions.checkNotNull(dataType);
        this.template = template;
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
        return new FieldDef(this.name, this.dataType, new StringTemplateValue(template));
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("name", name).add("dataType", dataType).toString();
    }

    public static FieldDef fd(final DataType dataType) {
        Preconditions.checkNotNull(dataType);
        return new FieldDef(null, dataType, null);
    }
}
