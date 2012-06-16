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

package de.brands4friends.daleq.integration.tests.assertingtable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.brands4friends.daleq.Daleq;
import de.brands4friends.daleq.DaleqSupport;
import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.integration.IntegrationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationConfig.class)
public class AssertTableTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DaleqSupport daleq;

    @Test
    public void inserting_build_asserting() {
        final Table toBeInserted = Daleq.aTable(AssertTableTable.class).withRowsUntil(100);
        daleq.insertIntoDatabase(toBeInserted);

        final Table toBeCompared = Daleq.aTable(AssertTableTable.class).withRowsUntil(100);
        daleq.assertTableInDatabase(toBeCompared);
    }
}
