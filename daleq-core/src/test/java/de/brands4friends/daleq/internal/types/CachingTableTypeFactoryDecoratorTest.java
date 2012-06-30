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

package de.brands4friends.daleq.internal.types;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import de.brands4friends.daleq.Daleq;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TableDef;
import de.brands4friends.daleq.TableType;
import de.brands4friends.daleq.TableTypeFactory;

public class CachingTableTypeFactoryDecoratorTest extends EasyMockSupport {

    private TableTypeFactory delegate;
    private CachingTableTypeFactoryDecorator decorator;
    private TableType tableType;

    @TableDef("THE_TABLE")
    public static class TheTable {
        public static final FieldDef ID = Daleq.fd(DataType.INTEGER);
    }

    @Before
    public void setUp() throws Exception {
        delegate = createMock(TableTypeFactory.class);
        decorator = new CachingTableTypeFactoryDecorator(delegate);
        tableType = new TableTypeFactoryImpl().create(TheTable.class);
    }

    @Test
    public void onCacheMiss_should_callDelegate() {
        expectCallDelegate();

        replayAll();
        final TableType result = decorator.create(TheTable.class);
        assertThat(result, is(tableType));
        verifyAll();
    }

    @Test
    public void onCacheHit_should_notDelegate() {
        expectCallDelegate();

        replayAll();
        decorator.create(TheTable.class);
        decorator.create(TheTable.class);
        verifyAll();
    }

    private void expectCallDelegate() {
        expect(delegate.create(TheTable.class)).andReturn(tableType).times(1);
    }

}
