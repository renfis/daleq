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

import javax.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import de.brands4friends.daleq.FieldData;

public final class FieldContainerImpl implements FieldData {
    private final String name;
    private final Optional<String> value;

    public FieldContainerImpl(final String name, @Nullable final String value) {
        this.name = Preconditions.checkNotNull(name);
        this.value = Optional.fromNullable(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof FieldContainerImpl) {
            final FieldContainerImpl that = (FieldContainerImpl) obj;

            return Objects.equal(name, that.name)
                    && Objects.equal(value, that.value);
        }

        return false;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
