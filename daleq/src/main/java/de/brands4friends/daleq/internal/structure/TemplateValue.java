package de.brands4friends.daleq.internal.structure;

import org.apache.commons.lang.text.StrSubstitutor;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

public class TemplateValue {

    private static final String VAR_NAME = "_";

    public static final TemplateValue DEFAULT = new TemplateValue("${" + VAR_NAME + "}");

    private final String template;

    public TemplateValue(final String template) {
        this.template = template;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof TemplateValue) {
            final TemplateValue that = (TemplateValue) obj;

            return Objects.equal(template, that.template);
        }

        return false;
    }

    public String render(final String value){
        return StrSubstitutor.replace(this.template, ImmutableMap.of(VAR_NAME,value));
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
