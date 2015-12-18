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

package de.brands4friends.daleq.core;

import com.google.common.collect.Lists;

public final class TemplateValues {

    public static final Base64TemplateValue BASE_64_TEMPLATE_VALUE = new Base64TemplateValue();
    public static final CharTemplateValue CHAR_TEMPLATE_VALUE = new CharTemplateValue();
    public static final DateTemplateValue DATE_TEMPLATE_VALUE = new DateTemplateValue();
    public static final TimestampTemplateValue TIMESTAMP_TEMPLATE_VALUE =
            new TimestampTemplateValue(Integer.MAX_VALUE / 2);
    public static final TimeTemplateValue TIME_TEMPLATE_VALUE = new TimeTemplateValue();

    private TemplateValues() {

    }

    public static TemplateValue base64() {
        return BASE_64_TEMPLATE_VALUE;
    }

    public static TemplateValue character() {
        return CHAR_TEMPLATE_VALUE;
    }

    public static TemplateValue date() {
        return DATE_TEMPLATE_VALUE;
    }

    /**
     * Returns a TemplateValue which maps the row id to values within the range [0,modulus).
     *
     * @param modulus a modulus &gt; 0.
     * @return Returns a TemplateValue which maps the row id to values within the range [0,modulus).
     * @throws IllegalArgumentException if modulus &lt;= 0
     */
    public static TemplateValue modulo(final long modulus) {
        return new ModuloTemplateValue(modulus);
    }

    public static TemplateValue string(final String template) {
        return new StringTemplateValue(template);
    }

    public static TemplateValue timestamp() {
        return TIMESTAMP_TEMPLATE_VALUE;
    }

    public static TemplateValue time() {
        return TIME_TEMPLATE_VALUE;
    }

    @SafeVarargs
    public static <T> TemplateValue enumeration(final T... options) {
        return new EnumeratingTemplateValue<T>(Lists.newArrayList(options));
    }
}
