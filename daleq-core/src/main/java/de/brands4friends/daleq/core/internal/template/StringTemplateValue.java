/*
 * Copyright 2012 brands4friends, Private Sale GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.brands4friends.daleq.core.internal.template;

import org.apache.commons.lang.text.StrSubstitutor;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

import de.brands4friends.daleq.core.TemplateValue;

public final class StringTemplateValue implements TemplateValue {

    private static final String VAR_NAME = "_";
    public static final String VAR = "${" + VAR_NAME + "}";

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
