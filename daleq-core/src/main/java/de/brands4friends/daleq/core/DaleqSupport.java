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

import java.io.IOException;
import java.io.PrintStream;

/**
 * Provides the Daleq infrastructure to a Unit Test.
 * <p/>
 * This class is intended to be used in a unit test. If Java would support Mixins, this would be a candidate. But
 * it ain't. Hence a Unit Test can only delegate to this class. Nonetheless, this class is designed to make
 * such a delegation as painless as possible.
 */
public interface DaleqSupport {

    /**
     * Inserts the <code>Table</code>s into the database.
     * <p/>
     * Once the table content has been defined, the respective {@link TableData} is created and inserted
     * into a configured database. The tables are inserted in the order they are given to the method. This
     * has to be considered especially if foreign key constraints must be fulfilled.
     *
     * @param tables the Daleq tables, which are should be filled into the database.
     * @throws DaleqException if an error occurred in the underlying infrastructure.
     */
    void insertIntoDatabase(Table... tables);

    /**
     * Checks whether the actual table in the database has the same content as the given <code>table</code>.
     * <p/>
     * Compares the given <code>table</code> to the content of the table with the same name in the database.
     * Details of the comparison depend on the actual implementation.
     *
     * @param table         the data in this table is compared to the data in the database.
     * @param ignoreColumns These fields will be ignored when the table is compared to the database.
     *                      This is usually used, when you need to ignore values that are created by
     *                      the database itself and therefore out of the test's scope. Usually this
     *                      applies to primary keys assigned by sequence.
     * @throws AssertionError if
     */
    void assertTableInDatabase(Table table, final FieldDef... ignoreColumns);

    /**
     * Prints the <code>table</code> content to the given <code>printer</code>.
     * <p/>
     * This method is intended for debug purposes, if you really need to see, what Daleq has created for you.
     *
     * @param table   the <code>Table</code> that should be printed to the <code>printer</code>.
     * @param printer a <code>PrintStream</code>, which receives the <code>table</code>.
     * @throws IOException if an error occurred while writing to <code>printer</code>.
     */
    void printTable(Table table, PrintStream printer) throws IOException;
}
