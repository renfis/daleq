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

import static de.brands4friends.daleq.core.Daleq.aRow;
import static de.brands4friends.daleq.core.Daleq.aTable;
import static de.brands4friends.daleq.integration.config.SupportedDb.MYSQL;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.junit.Test;

import de.brands4friends.daleq.core.Table;
import de.brands4friends.daleq.integration.rules.Restrict;
import de.brands4friends.daleq.integration.tables.TimestampTypeTable;

public class TimestampTest extends BaseTest {

    @Test
    @Restrict(not = MYSQL, reason = "Mysql does not support milliseconds precision in Timestamps")
    public void timestamp_should_haveProperMillis() {

        final DateTime dateTime = new DateTime(2012, 10, 5, 16, 0, 0, 560);

        final Timestamp ts = new Timestamp(dateTime.getMillis());
        final Table table = aTable(TimestampTypeTable.class).with(
                aRow(42).f(TimestampTypeTable.A_TIMESTAMP, ts)
        );
        daleq.insertIntoDatabase(table);

        final Table expected = aTable(TimestampTypeTable.class).with(
                aRow(42).f(TimestampTypeTable.A_TIMESTAMP, dateTime)
        );

        daleq.assertTableInDatabase(expected);

    }

    @Test
    @Restrict(not = MYSQL, reason = "Mysql does not support milliseconds precision in Timestamps")
    public void dateTime_should_haveProperMillis() {

        final DateTime dateTime = new DateTime(2012, 10, 5, 16, 0, 0, 560);

        final Table table = aTable(TimestampTypeTable.class).with(
                aRow(42).f(TimestampTypeTable.A_TIMESTAMP, dateTime)
        );
        daleq.insertIntoDatabase(table);

        final Table expected = aTable(TimestampTypeTable.class).with(
                aRow(42).f(TimestampTypeTable.A_TIMESTAMP, dateTime)
        );

        daleq.assertTableInDatabase(expected);

    }

}
