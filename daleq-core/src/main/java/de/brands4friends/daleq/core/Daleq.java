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

import de.brands4friends.daleq.core.internal.types.ClassBasedTableTypeReference;

/**
 * Daleq's Embedded DSL.
 * <p>
 * The static methods in this class are the foundation of Daleq's embedded DSL.They are
 * intended for static import
 * <pre class="code">{@code
 * import static de.brands4friends.daleq.core.Daleq.*;
 * }</pre>
 * <p>
 * and then to be used as
 * <p>
 * <pre class="code">{@code
 * final Table products = aTable(ProductTable.class).with(
 * aRow(PRODUCT_1).f(PRICE, "1.00"),
 * aRow(PRODUCT_10).f(PRICE, "10.00"),
 * aRow(PRODUCT_9_99).f(PRICE, "9.99"),
 * aRow(PRODUCT_50).f(PRICE, "50.00")
 * );
 * }</pre>
 */
public final class Daleq {

    private Daleq() {

    }

    /**
     * Builds a {@link Table} of the type defined by <code>fromClass</code>.
     * <p>
     * Daleq needs to know about a table's meta model. One option to define a meta model is Daleq's
     * class based table definition approach, as done with {@link TableDef}. This method builds
     * a table which assumes that <code>fromClass</code> holds such a meta model.
     *
     * @param fromClass a class annotated with {@link TableDef} that defines a class based table definition.
     * @return a <code>Table</code> which is ready to receive some rows.
     * @see TableDef
     * @see Table
     */
    public static <T> Table aTable(final Class<T> fromClass) {
        return new TableBuilder(ClassBasedTableTypeReference.of(fromClass));
    }

    /**
     * Builds a {@link Row} with the given <code>id</code>.
     * <p>
     *
     * @param id the <code>Row</code>'s id, as defined by {@link Row}.
     * @return a <code>Row</code>, which should ultimately be added to a <code>Table</code>.
     * @see Row
     */
    public static Row aRow(final long id) {
        return new RowBuilder(id);
    }

    /**
     * Defines a Field in the class based table definition approach.
     *
     * @param dataType the field's dataType.
     * @return a Field Definition
     * @throws NullPointerException if <code>dataType</code> is <code>null</code>.
     */
    public static FieldDef fd(final DataType dataType) {
        return FieldDefBuilder.fd(dataType);
    }
}
