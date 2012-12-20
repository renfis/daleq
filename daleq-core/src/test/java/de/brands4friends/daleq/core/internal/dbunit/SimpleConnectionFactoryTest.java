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

package de.brands4friends.daleq.core.internal.dbunit;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

public class SimpleConnectionFactoryTest extends EasyMockSupport {

    private SimpleConnectionFactory connectionFactory;

    @Before
    public void setUp() throws Exception {
        connectionFactory = new SimpleConnectionFactory();
    }

    @Test(expected = NullPointerException.class)
    public void createConnectionWithoutDataSource_should_fail() {
        connectionFactory.createConnection();
    }

    @Test @SuppressWarnings("PMD.CloseResource")
    public void createConnection_should_haveDataTypeFactory() throws SQLException {
        final DataSource dataSource = createMock(DataSource.class);
        final IDataTypeFactory dataTypeFactory = createMock(IDataTypeFactory.class);
        final Connection conn = EasyMock.createNiceMock(Connection.class);

        connectionFactory.setDataSource(dataSource);
        connectionFactory.setDataTypeFactory(dataTypeFactory);

        expect(dataSource.getConnection()).andReturn(conn);

        replayAll();

        final IDatabaseConnection connection = connectionFactory.createConnection();
        final IDataTypeFactory actual = (IDataTypeFactory)
                connection.getConfig().getProperty("http://www.dbunit.org/properties/datatypeFactory");
        assertThat(actual, is(dataTypeFactory));

        verifyAll();
    }
}
