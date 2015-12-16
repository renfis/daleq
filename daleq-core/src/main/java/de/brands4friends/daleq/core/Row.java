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

/**
 * Used to build a <code>Row</code> in a relational database table.
 * <p>
 * Logically, <code>Row</code> represents a row in a table. It has an id and consists of a list of fields. Fields
 * are not modelled explicitly, which means there is no class representing fields, but only through
 * <code>Row</code>'s capability to specify the content of a field. A row always belongs to {@link Table}.
 * <p>
 * A row is always created and associated with an id.
 * This id is used to either define the value of the primary key and to be used as a source for Daleq to
 * deterministically choose the value of an non explicitly specified field. It is Daleq's basic idea to just
 * specify what is subject to a particular test and to leave out what is unimportant. Nonetheless a database table
 * has to be filled with some content. Therefore Daleq decides for each field, which has not been defined explicitly
 * with {@link #f}, which value is set to the field. Generally this depends on the field's particular type and sometimes
 * even on the respective database, the table is written to. Daleq takes care, that each value is created in a
 * deterministic fashion, hence each run of a test is faced with exactly the same data.
 * <p>
 * Technically, <code>Row</code> is a builder. It will collect the <code>Row</code>'s id and all explicitly set
 * fields to build the data holding entity {@link RowData}.
 * <p>
 * Use {@link Daleq#aRow} to create an instance of <code>Row</code>. We encourage to static import this method to
 * benefit from Daleq's embedded DSL.
 *
 * @see Table
 * @see Daleq
 * @see de.brands4friends.daleq.core.internal.conversion.TypeConverter
 */
public interface Row {

    /**
     * Defines the value of a field.
     * <p>
     * If a field is defined multiple times, the last definition is used.
     *
     * @param fieldTypeReference References the field, which should be set. Since the row will finally
     *                           end up in a {@link Table}, the Field has to belong the table's meta model. As
     *                           matter of fact this is not checked, when field is added with method call,
     *                           but rather when the table is built.
     * @param value              Sets the fields value. Daleq has a sophisticated strategy to calculate the value which
     *                           is finally written into the database. If <code>value</code> is a (boxed) primitive
     *                           type, it is directly written to the DB. If it is a subclass of Object, then Daleq
     *                           checks if there is a registered
     *                           {@link de.brands4friends.daleq.core.internal.conversion.TypeConverter}
     *                           for this class and uses it, otherwise the class' toString() method is used. If you want
     *                           to write <code>null</code> to the database, just set If <code>value</code>
     *                           to <code>null</code>.
     * @return the instance of row itself
     * @throws NullPointerException if fieldTypeReference is null
     */
    Row f(FieldTypeReference fieldTypeReference, @Nullable Object value);

    /**
     * Constructs the actual data holding entity.
     * <p>
     * Since <code>Row</code> is just a builder, at some point Daleq has to construct the entities, holding
     * the database table content. In general this method is not expected to be called by client code, but only
     * by Daleq's internal code.
     *
     * @param context   provides contextual information, which are required to build a {@link RowData}
     * @param tableType the type of the meta model, to which the row belongs.
     * @return an entity holding the row's content
     * @throws NullPointerException      if context is null
     * @throws NoSuchDaleqFieldException if any field in the row contains does not belong to
     *                                   the table's meta model.
     */
    RowData build(Context context, final TableType tableType);
}
