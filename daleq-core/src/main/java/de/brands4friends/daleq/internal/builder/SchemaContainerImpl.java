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

package de.brands4friends.daleq.internal.builder;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.brands4friends.daleq.SchemaContainer;
import de.brands4friends.daleq.TableData;

public final class SchemaContainerImpl implements SchemaContainer {

    private final List<TableData> tables;

    public SchemaContainerImpl(final List<TableData> tables) {
        this.tables = ImmutableList.copyOf(Preconditions.checkNotNull(tables));
    }

    @Override
    public List<TableData> getTables() {
        return tables;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof SchemaContainerImpl) {
            final SchemaContainerImpl that = (SchemaContainerImpl) obj;

            return Objects.equal(tables, that.tables);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tables);
    }
}
