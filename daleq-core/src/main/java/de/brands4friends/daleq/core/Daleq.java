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

import de.brands4friends.daleq.core.internal.builder.FieldDefBuilder;
import de.brands4friends.daleq.core.internal.builder.RowBuilder;
import de.brands4friends.daleq.core.internal.builder.TableBuilder;

public final class Daleq {

    private Daleq() {

    }

    public static <T> Table aTable(final Class<T> fromClass) {
        return TableBuilder.aTable(fromClass);
    }

    public static Row aRow(final long id) {
        return RowBuilder.aRow(id);
    }

    public static FieldDef fd(final DataType dataType) {
        return FieldDefBuilder.fd(dataType);
    }
}
