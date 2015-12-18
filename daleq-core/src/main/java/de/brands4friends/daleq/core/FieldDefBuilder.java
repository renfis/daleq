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

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

final class FieldDefBuilder implements FieldDef {

    private final DataType dataType;
    private final Optional<String> name;
    private final Optional<TemplateValue> template;

    private FieldDefBuilder(
            final DataType dataType,
            final Optional<String> name,
            final Optional<TemplateValue> template) {
        this.dataType = Preconditions.checkNotNull(dataType);
        this.name = Preconditions.checkNotNull(name);
        this.template = Preconditions.checkNotNull(template);
    }

    @Override
    public Optional<String> getName() {
        return name;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public Optional<TemplateValue> getTemplate() {
        return template;
    }

    @Override
    public FieldDef name(final String name) {
        Preconditions.checkNotNull(name);
        return new FieldDefBuilder(this.dataType, Optional.of(name), this.template);
    }

    @Override
    public FieldDef template(final String template) {
        Preconditions.checkNotNull(template);
        return new FieldDefBuilder(
                this.dataType,
                this.name,
                Optional.<TemplateValue>of(new StringTemplateValue(template))
        );
    }

    @Override
    public FieldDef template(final TemplateValue templateValue) {
        Preconditions.checkNotNull(templateValue);
        return new FieldDefBuilder(
                this.dataType,
                this.name,
                Optional.of(templateValue)
        );
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", name).add("dataType", dataType).toString();
    }

    public static FieldDef fd(final DataType dataType) {
        Preconditions.checkNotNull(dataType);
        return new FieldDefBuilder(dataType, Optional.<String>absent(), Optional.<TemplateValue>absent());
    }

    @Override
    public FieldType resolve(final TableType tableType) {
        return tableType.findFieldBy(this);
    }
}
