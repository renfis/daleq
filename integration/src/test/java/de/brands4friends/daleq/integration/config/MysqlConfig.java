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
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import de.brands4friends.daleq.integration.tables.MysqlAllTypesTable;
import de.brands4friends.daleq.integration.tables.MysqlAssertTableTable;

public class MysqlConfig {

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:mysql://127.0.0.1:3306/test", "test", "test");
    }

    @Bean
    public IDataTypeFactory dataTypeFactory() {
        return new MySqlDataTypeFactory();
    }

    @Bean
    public TableProvider allTypesProvider() {
        return new TableProvider(MysqlAllTypesTable.class, MysqlAssertTableTable.class, MysqlAssertTableTable.ID);
    }

    @Bean
    public RunDbBarrier runDbBarrier() {
        return new RunDbBarrier(true);
    }

    @Bean
    public PrepareMysqlSchema prepareMysqlSchema() {
        return new PrepareMysqlSchema(dataSource());
    }
}
