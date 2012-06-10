package de.brands4friends.daleq.internal.types;

import static com.google.common.base.Objects.toStringHelper;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TemplateValue;

public final class FieldType {
    private final String name;
    private final DataType dataType;
    private final Optional<TemplateValue> templateValue;
    private final FieldDef origin;


    public FieldType(
            final String name,
            final DataType dataType,
            final Optional<TemplateValue> templateValue,
            final FieldDef origin) {
        this.name = Preconditions.checkNotNull(name);
        this.dataType = Preconditions.checkNotNull(dataType);
        this.templateValue = Preconditions.checkNotNull(templateValue);
        this.origin = Preconditions.checkNotNull(origin);
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Optional<TemplateValue> getTemplateValue() {
        return templateValue;
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
