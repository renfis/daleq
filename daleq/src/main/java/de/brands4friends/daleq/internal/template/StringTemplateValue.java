package de.brands4friends.daleq.internal.template;

import org.apache.commons.lang.text.StrSubstitutor;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

public final class StringTemplateValue implements TemplateValue {

    private static final String VAR_NAME = "_";
    public static final String VAR = "${" + VAR_NAME + "}";

    public static final TemplateValue DEFAULT = new StringTemplateValue(VAR);

    private final String template;

    public StringTemplateValue(final String template) {
        this.template = template;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof StringTemplateValue) {
            final StringTemplateValue that = (StringTemplateValue) obj;

            return Objects.equal(template, that.template);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(template);
    }

    @Override
    public String render(final long value) {
        return StrSubstitutor.replace(this.template, ImmutableMap.of(VAR_NAME, value));
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("template", template).toString();
    }
}
