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

import com.google.common.base.Optional;

public interface FieldDef extends FieldTypeReference {
    Optional<String> getName();

    DataType getDataType();

    Optional<TemplateValue> getTemplate();

    FieldDef name(String name);

    FieldDef template(String template);

    FieldDef template(TemplateValue templateValue);
}
