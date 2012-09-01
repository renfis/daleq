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

package de.brands4friends.daleq.integration.tests;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.brands4friends.daleq.core.Daleq;
import de.brands4friends.daleq.core.Table;
import de.brands4friends.daleq.integration.beans.TableProvider;

public class AssertingTest extends BaseTest {

    @Autowired
    private TableProvider tableProvider;

    @Test
    public void inserting_build_asserting() {
        final Table toBeInserted = Daleq.aTable(tableProvider.assertTable()).withRowsBetween(1, 100);
        daleq.insertIntoDatabase(toBeInserted);

        final Table toBeCompared = Daleq.aTable(tableProvider.assertTable()).withRowsBetween(1, 100);
        daleq.assertTableInDatabase(toBeCompared, tableProvider.assertTableId());
    }

    @Test
    public void inserting_build_asserting2() {
        final Table toBeInserted = Daleq.aTable(tableProvider.assertTable()).withRowsBetween(1, 100);
        daleq.insertIntoDatabase(toBeInserted);

        final Table toBeCompared = Daleq.aTable(tableProvider.assertTable()).withRowsBetween(1, 100);
        daleq.assertTableInDatabase(toBeCompared, tableProvider.assertTableId());
    }
}
