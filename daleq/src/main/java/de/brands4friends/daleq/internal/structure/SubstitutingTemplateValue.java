package de.brands4friends.daleq.internal.structure;

import org.apache.commons.lang.text.StrSubstitutor;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

public class SubstitutingTemplateValue implements TemplateValue {

    private static final String VAR_NAME = "_";
    public static final String VAR = "${" + VAR_NAME + "}";

    public static final TemplateValue DEFAULT = new SubstitutingTemplateValue(VAR);

    private final String template;

    public SubstitutingTemplateValue(final String template) {
        this.template = template;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof SubstitutingTemplateValue) {
            final SubstitutingTemplateValue that = (SubstitutingTemplateValue) obj;

            return Objects.equal(template, that.template);
        }

        return false;
    }

    @Override
    public String render(final long value) {
        return StrSubstitutor.replace(this.template, ImmutableMap.of(VAR_NAME, value));
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(template);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("template", template).toString();
    }
}
