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

package de.brands4friends.daleq.internal.types;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.FieldDef;

public class TableType {

    private final String name;
    private final List<FieldType> fields;
    private final Map<FieldDef, FieldType> lookupByDef;

    TableType(final String name, final List<FieldType> fields) {
        this.name = name;
        this.fields = fields;
        this.lookupByDef = Maps.uniqueIndex(fields, new Function<FieldType, FieldDef>() {
            @Override
            public FieldDef apply(final FieldType input) {
                return input.getOrigin();
            }
        });
    }

    public TableType(final String name, final FieldType... fields) {
        this(name, Arrays.asList(fields));
    }

    public String getName() {
        return name;
    }

    public List<FieldType> getFields() {
        return fields;
    }

    public FieldType findStructureByDef(final FieldDef fieldDef) {
        return lookupByDef.get(fieldDef);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof TableType) {
            final TableType that = (TableType) obj;

            return Objects.equal(name, that.name)
                    && Objects.equal(fields, that.fields);
        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(name, fields);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("properties", fields).toString();
    }
}
