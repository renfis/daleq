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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.core.FieldType;
import de.brands4friends.daleq.core.FieldTypeReference;
import de.brands4friends.daleq.core.TableType;

final class TableTypeImpl implements TableType {

    private final String name;
    private final List<FieldType> fields;
    private final Map<FieldTypeReference, FieldType> lookupByDef;

    TableTypeImpl(final String name, final List<FieldType> fields) {
        this.name = name;
        this.fields = fields;
        this.lookupByDef = Maps.uniqueIndex(fields, new Function<FieldType, FieldTypeReference>() {
            @Override
            public FieldTypeReference apply(@Nullable final FieldType input) {
                if (input == null) {
                    throw new IllegalArgumentException("input");
                }
                return input.getOrigin();
            }
        });
    }

    public TableTypeImpl(final String name, final FieldType... fields) {
        this(name, Arrays.asList(fields));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<FieldType> getFields() {
        return fields;
    }

    @Override
    public FieldType findFieldBy(final FieldTypeReference fieldRef) {
        return lookupByDef.get(fieldRef);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TableTypeImpl) {
            final TableTypeImpl that = (TableTypeImpl) obj;

            return Objects.equal(name, that.name)
                    && Objects.equal(fields, that.fields);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, fields);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("properties", fields).toString();
    }
}
