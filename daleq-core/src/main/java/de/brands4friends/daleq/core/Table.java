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

import javax.annotation.Nullable;

public interface Table {

    Table with(Row... rows);

    Table withSomeRows(Iterable<Long> ids);

    Table withSomeRows(long... ids);

    Table withRowsUntil(long maxId);

    Table allHaving(FieldTypeReference fieldDef, @Nullable Object value);

    Table having(FieldTypeReference fieldDef, Iterable<Object> values);

    TableData build(final Context context);

}
