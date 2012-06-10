package de.brands4friends.daleq.internal.types;

import static com.google.common.base.Objects.toStringHelper;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Objects;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TemplateValue;

public final class FieldType {
    private final String name;
    private final DataType dataType;
    private final TemplateValue templateValue;
    private final FieldDef origin;


    public FieldType(
            final String name,
            final DataType dataType,
            final TemplateValue templateValue,
            final FieldDef origin) {
        this.name = name;
        this.dataType = dataType;
        this.templateValue = templateValue;
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public TemplateValue getTemplateValue() {
        return templateValue;
    }

    public boolean hasTemplateValue() {
        return templateValue != null;
    }

    public FieldDef getOrigin() {
        return origin;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof FieldType) {
            final FieldType that = (FieldType) obj;

            return Objects.equal(name, that.name)
                    && Objects.equal(dataType, that.dataType)
                    && Objects.equal(templateValue, that.templateValue);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, dataType, templateValue);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("name", name)
                .add("dataType", dataType)
                .add("templateValue", templateValue)
                .toString();
    }
}
