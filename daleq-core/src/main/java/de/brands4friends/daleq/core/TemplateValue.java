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

/**
 * Maps a {@link Row}'s id to a field value.
 * <p>
 * Daleq's core feature is making up a field's value, if a {@link Row} does not explicitly set its
 * field. A <code>TemplateValue</code> is the mechanism to map the <code>Row</code>'s id to
 * a particular value. Each {@link FieldDef} defines implicitly or explicitly a <code>TemplateValue</code>
 * for its representing Field. Hence it could be controlled on a per <code>FieldDef</code> level, how
 * Daleq will fill the relation database table.
 */
public interface TemplateValue {

    /**
     * Maps a <code>Row</code>'s id to a field value.
     * <p>
     * A field value may be of any type. It will be marshaled to a String by
     * {@link de.brands4friends.daleq.core.internal.conversion.TypeConversion}.
     *
     * @param value the <code>Row</code>'s id.
     * @return An object, which will be marshaled to a String.
     */
    Object transform(long value);
}
