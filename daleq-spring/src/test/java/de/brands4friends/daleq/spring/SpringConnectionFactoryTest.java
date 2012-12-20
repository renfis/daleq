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

package de.brands4friends.daleq.spring;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class SpringConnectionFactoryTest extends EasyMockSupport {

    private SpringConnectionFactory connectionFactory;

    @Before
    public void setUp() throws Exception {
        connectionFactory = new SpringConnectionFactory();
    }

    @Test(expected = NullPointerException.class)
    public void createConnectionWithoutDataSource_should_fail() {
        connectionFactory.createConnection();
    }

    @Test
    public void createConnection_should_haveDataTypeFactory() {
        final DataSource dataSource = new EmbeddedDatabaseBuilder().build();
        final IDataTypeFactory dataTypeFactory = createMock(IDataTypeFactory.class);

        connectionFactory.setDataSource(dataSource);
        connectionFactory.setDataTypeFactory(dataTypeFactory);

        final IDatabaseConnection connection = connectionFactory.createConnection();
        final IDataTypeFactory actual = (IDataTypeFactory)
                connection.getConfig().getProperty("http://www.dbunit.org/properties/datatypeFactory");
        assertThat(actual, is(dataTypeFactory));
    }
}
