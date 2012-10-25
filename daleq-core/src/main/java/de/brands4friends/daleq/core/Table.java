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
 * to construct these rows. Together with {@link Row} it builds Daleq's model to describe the content of a database
 * table.
 * <p/>
 * Use {@link Daleq#aTable} to create a <code>Table</code>. We encourage to static import this method to benefit from
 * Daleq's embedded DSL.
 *
 * @see Daleq
 * @see Row
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
     * @return a <code>Table</code> containing the new rows.
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
     * @return a <code>Table</code> containing the new rows.
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
     * @return a <code>Table</code> containing the new rows.
     * @throws NullPointerException if either the parameter <code>ids</code> or one element in it is null
     */
    Table withSomeRows(long... ids);

    /**
     * Adds <code>maxId</code> number of rows with the ids from 0 to <code>maxId - 1</code> to the table.
     * <p/>
     * The method should not be used anymore and will be removed in future releases.
     *
     * @param maxId describes the upper (exclusive) limit of the ids which will be added, starting form 0.
     * @return a <code>Table</code> containing the new rows.
     * @deprecated The method was supposed to be used for convenience, but meanwhile we learned that it is not
     *             intuitive. On one hand it is not a good idea to start ids form 0, because some DBs treat 0 as an
     *             id special, especially in combination with sequences. For instance, Mysql will not let you explicitly
     *             set a a field, which has the auto increment attribute, with a zero, only non zero values could be
     *             set explicitly. On the other hand, specifying the maxId as an exclusive interval is not intuitive
     *             in this context. Use {@link #withRowsBetween} instead.
     */
    @Deprecated
    Table withRowsUntil(long maxId);

    /**
     * Adds rows with all ids in the interval <code>[from,to]</code> to the table.
     * <p/>
     * A convience method, which assumes an interval starting at <code>from</code> up to and including <code>to</code>
     * and consisting of all ids in between. Each id in this interval will be transformed to a row, like
     * <code>aRow(id)</code> would do it and add it to the table.
     * <p/>
     * For instance
     * <pre class="code">{@code
     * aTable(ProductTable.class).withRowsBetween(1L, 4L);
     * }</pre>
     * will yield a table
     * <table>
     * <tr><th>ID</th><th>NAME</th><th>SIZE</th><th>PRICE</th></tr>
     * <tr><td>1</td><td>?</td><td>?</td><td>?</td></tr>
     * <tr><td>2</td><td>?</td><td>?</td><td>?</td></tr>
     * <tr><td>3</td><td>?</td><td>?</td><td>?</td></tr>
     * <tr><td>4</td><td>?</td><td>?</td><td>?</td></tr>
     * </table>
     *
     * @param from the lower inclusive bound of the interval. Has to be {@code >=0}.
     * @param to   the upper inclusive bound of the internval. Has to be {@code >=0} and {@code from < to}.
     * @return a <code>Table</code> containing the new rows.
     * @throws IllegalArgumentException if not
     *                                  <ol>
     *                                  <li>{@code from >=0}</li>
     *                                  <li>{@code to >=0}</li>
     *                                  <li>{@code from < to}</li>
     *                                  </ol>
     */
    Table withRowsBetween(long from, long to);

    /**
     * Sets the <code>field</code> in all already existing rows to the given <code>values</code>.
     * <p/>
     * A convenience method to batch change all already existing rows in the table. It is expected that
     * <code>field</code> belongs to this <code>Table</code>'s {@link TableDef}. However, this is not checked
     * while being added to the table, but only when <code>build()</code> is called.
     * <p/>
     * The already existing rows are expected to have the order in which they were added. The values are applied
     * to all those rows in that order. Hence the respective field in the <code>i</code>-th row will get
     * the <code>i</code>-th value from <code>values</code>. If there are more rows than values, than only the
     * first rows get their fields set, the other rows will not have their field changed. On the other hand, i
     * there are more values than rows, only the first values are used to be assigned to the rows.
     * <p/>
     * Note that rows added to the table after this method has been called, will not be affected by it.
     *
     * @param field  References the field, which is expected to be contained in code>Table</code>'s {@link TableDef}.
     *               The respective fields in all rows in the table, will be changed.
     * @param values the values will be assigned to the respective field in all rows, already existing in the table.
     *               The value could be of any type Daleq is able to convert. See {@link Row} for further details.
     * @return a <code>Table</code> containing the changed rows.
     * @throws NullPointerException if field is null
     */
    Table having(FieldTypeReference field, Object... values);

    /**
     * Sets the <code>field</code> in all already existing rows the same given <code>value</code>.
     * <p/>
     * A convenience method to batch change all already existing rows in the table. It is expected that
     * <code>field</code> belongs to this <code>Table</code>'s {@link TableDef}. However, this is not checked
     * while being added to the table, but only when <code>build()</code> is called.
     * <p/>
     * All rows in the table get their <code>field</code> updated with the given <code>value</code>
     * <p/>
     * Note that rows added to the table after this method has been called, will not be affected by it.
     *
     * @param field References the field, which is expected to be contained in code>Table</code>'s {@link TableDef}.
     *              The respective fields in all rows in the table, will be changed.
     * @param value the value will be assigned to the respective field in all rows, already existing in the table.
     *              The value could be of any type Daleq is able to convert. See {@link Row} for further details.
     * @return a <code>Table</code> containing the changed rows.
     * @throws NullPointerException if field is null
     */
    Table allHaving(FieldTypeReference field, @Nullable Object value);

    /**
     * Sets the <code>field</code> in all already existing rows to the given <code>values</code>.
     * <p/>
     * A convenience method to batch change all already existing rows in the table.
     *
     * @param field  References the field, which is expected to be contained in code>Table</code>'s {@link TableDef}.
     *               The respective fields in all rows in the table, will be changed.
     * @param values the values will be assigned to the respective field in all rows, already existing in the table.
     *               The value could be of any type Daleq is able to convert. See {@link Row} for further details.
     * @return a <code>Table</code> containing the changed rows.
     * @throws NullPointerException if field or values is null
     * @see #having(FieldTypeReference, Object...)
     */
    Table havingIterable(FieldTypeReference field, Iterable<Object> values);

    TableData build(final Context context);

}
