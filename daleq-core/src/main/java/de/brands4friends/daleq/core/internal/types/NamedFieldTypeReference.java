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

package de.brands4friends.daleq.core.internal.types;

import static com.google.common.base.Preconditions.checkNotNull;

import de.brands4friends.daleq.core.FieldType;
import de.brands4friends.daleq.core.FieldTypeReference;
import de.brands4friends.daleq.core.TableType;

public class NamedFieldTypeReference implements FieldTypeReference {

    private final String fieldName;

    public NamedFieldTypeReference(final String fieldName) {
        this.fieldName = checkNotNull(fieldName);
    }

    @Override
    public FieldType resolve(final TableType tableType) {
        if (tableType == null) {
            throw new IllegalArgumentException();
        }

        for (FieldType fieldType : tableType.getFields()) {
            if (fieldType.getName().equals(fieldName)) {
                return fieldType;
            }
        }

        return null;
    }
}
