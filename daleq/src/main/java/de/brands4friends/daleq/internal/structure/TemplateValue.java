package de.brands4friends.daleq.internal.structure;

import com.google.common.base.Objects;

public class TemplateValue {

    public static final TemplateValue DEFAULT = new TemplateValue("${_}");

    private final String template;

    public TemplateValue(final String template) {
        this.template = template;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof TemplateValue) {
            final TemplateValue that = (TemplateValue) obj;

            return Objects.equal(template, that.template);
        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(template);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("template",template).toString();
    }
}
