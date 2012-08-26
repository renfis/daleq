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

package de.brands4friends.daleq.integration.config;

import javax.sql.DataSource;

import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import de.brands4friends.daleq.core.DaleqSupport;
import de.brands4friends.daleq.core.internal.dbunit.ConnectionFactory;
import de.brands4friends.daleq.core.internal.dbunit.DbUnitDaleqSupport;
import de.brands4friends.daleq.spring.SpringConnectionFactory;

@Configuration
public class IntegrationConfig {

    @Bean
    public PlatformTransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DaleqSupport daleqSupport(final ConnectionFactory connectionFactory) {
        return DbUnitDaleqSupport.createInstance(connectionFactory);
    }

    @Bean
    public ConnectionFactory connectionFactory(final DataSource dataSource, final IDataTypeFactory dataTypeFactory) {
        final SpringConnectionFactory connectionFactory = new SpringConnectionFactory();
        connectionFactory.setDataTypeFactory(dataTypeFactory);
        connectionFactory.setDataSource(dataSource);
        return connectionFactory;
    }
}