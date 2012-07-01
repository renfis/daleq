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

package de.brands4friends.daleq.core.internal.builder;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import de.brands4friends.daleq.core.FieldDef;

public class FieldHolder {
    private final FieldDef fieldDef;
    private final Object value;

    public FieldHolder(final FieldDef fieldDef, final Object value) {
        this.fieldDef = Preconditions.checkNotNull(fieldDef);
        this.value = value;
    }

    public FieldDef getFieldDef() {
        return fieldDef;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(fieldDef, value);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof FieldHolder) {
            final FieldHolder that = (FieldHolder) obj;

            return Objects.equal(fieldDef, that.fieldDef)
                    && Objects.equal(value, that.value);
        }

        return false;
    }
}
