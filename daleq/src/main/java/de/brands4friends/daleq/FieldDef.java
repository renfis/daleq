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

package de.brands4friends.daleq;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import de.brands4friends.daleq.internal.template.StringTemplateValue;

public final class FieldDef {

    private final DataType dataType;
    private final Optional<String> name;
    private final Optional<TemplateValue> template;

    private FieldDef(final DataType dataType, final Optional<String> name, final Optional<TemplateValue> template) {
        this.dataType = Preconditions.checkNotNull(dataType);
        this.name = Preconditions.checkNotNull(name);
        this.template = Preconditions.checkNotNull(template);
    }

    public Optional<String> getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public Optional<TemplateValue> getTemplate() {
        return template;
    }

    public FieldDef name(final String name) {
        return new FieldDef(this.dataType, Optional.of(name), this.template);
    }

    public FieldDef template(final String template) {
        return new FieldDef(
                this.dataType,
                Optional.<String>absent(),
                Optional.<TemplateValue>of(new StringTemplateValue(template))
        );
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("name", name).add("dataType", dataType).toString();
    }

    public static FieldDef fd(final DataType dataType) {
        Preconditions.checkNotNull(dataType);
        return new FieldDef(dataType, Optional.<String>absent(), Optional.<TemplateValue>absent());
    }
}
