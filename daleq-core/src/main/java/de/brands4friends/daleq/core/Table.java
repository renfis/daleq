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
 * Used to build the content of a relational database table.
 * <p/>
 * <code>Table</code> is a container which holds a list of {@link Row}s. Therefore it provides a set of builder methods
 * to construct these rows.
 * <p/>
 * Use {@link Daleq#aTable} to create a <code>Table</code>. We encourage to static import this method to benefit from
 * Daleq's embedded DSL.
 */
public interface Table {

    /**
     * Adds <code>rows</code> to the table.
     * <p/>
     * For instance
     * <pre class="code">{@code
     * aTable(ProductTable.class)
     * .rows(
     * aRow(1).f(SIZE,"XL"),
     * aRow(2)
     * )
     * }</pre>
     * will yield a table containing
     * <table>
     * <tr><th>ID</th><th>NAME</th><th>SIZE</th><th>PRICE</th></tr>
     * <tr><td>1</td><td>?</td><td>XL</td><td>?</td></tr>
     * <tr><td>2</td><td>?</td><td>?</td><td>?</td></tr>
     * </table>
     *
     * @param rows all rows which should be added to the table
     * @return a <code>Table</code> which has the new rows.
     * @throws NullPointerException if one instance of <code>rows</code> is <code>null</code>.
     */
    Table with(Row... rows);

    /**
     * Adds rows with the given <code>ids</code> to the table.
     * <p/>
     * A convenience method, which transforms each id in <code>ids</code> to a row, like <code>aRow(id)</code> would do,
     * and adds it to the already existing rows in the table.
     * <p/>
     * For instance
     * <pre class="code">{@code
     * final List<Long> ids = Lists.newArrayList(1L,2L);
     * aTable(ProductTable.class).withSomeRows(ids);
     * }</pre>
     * will yield a table
     * <table>
     * <tr><th>ID</th><th>NAME</th><th>SIZE</th><th>PRICE</th></tr>
     * <tr><td>1</td><td>?</td><td>?</td><td>?</td></tr>
     * <tr><td>2</td><td>?</td><td>?</td><td>?</td></tr>
     * </table>
     *
     * @param ids the ids of those rows, which will be added to the table.
     * @return a <code>Table</code> which contains the new rows.
     * @throws NullPointerException if either the parameter <code>ids</code> or one element in it is null
     */
    Table withSomeRows(Iterable<Long> ids);

    /**
     * Adds rows with the given <code>ids</code> to the table.
     * <p/>
     * A convenience method, which transforms each id in <code>ids</code> to a row, like <code>aRow(id)</code> would do,
     * and adds it to the already existing rows in the table.
     * <p/>
     * For instance
     * <pre class="code">{@code
     * aTable(ProductTable.class).withSomeRows(1L, 2L);
     * }</pre>
     * will yield a table
     * <table>
     * <tr><th>ID</th><th>NAME</th><th>SIZE</th><th>PRICE</th></tr>
     * <tr><td>1</td><td>?</td><td>?</td><td>?</td></tr>
     * <tr><td>2</td><td>?</td><td>?</td><td>?</td></tr>
     * </table>
     *
     * @param ids the ids of those rows, which will be added to the table.
     * @return a <code>Table</code> which contains the new rows.
     * @throws NullPointerException if either the parameter <code>ids</code> or one element in it is null
     */
    Table withSomeRows(long... ids);

    /**
     * Adds <code>maxId</code> number of rows with the ids from 0 to <code>maxId - 1</code> to the table.
     * <p/>
     * The method should not be used anymore and will be removed in future releases.
     *
     * @param maxId describes the upper (exclusive) limit of the ids which will be added, starting form 0.
     * @return a <code>Table</code> which contains the new rows.
     * @deprecated The method was supposed to be used for convenience, but meanwhile we learned that it is not
     *             intuitive. On one hand it is not a good idea to start ids form 0, because some DBs treat 0 as an
     *             id special, especially in combination with sequences. For instance, Mysql will not let you explicitly
     *             set a a field, which has the auto increment attribute, with a zero, only non zero values could be
     *             set explicitly. On the other hand, specifying the maxId as an exclusive interval is not intuitive
     *             in this context. Use {@link #withRowsBetween} instead.
     */
    Table withRowsUntil(long maxId);

    Table withRowsBetween(long from, long to);

    Table having(FieldTypeReference fieldDef, Object... values);

    Table allHaving(FieldTypeReference fieldDef, @Nullable Object value);

    Table havingIterable(FieldTypeReference fieldDef, Iterable<Object> values);

    TableData build(final Context context);

}
