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
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import de.brands4friends.daleq.integration.beans.TableProvider;
import de.brands4friends.daleq.integration.tables.HsqldbAllTypesTable;
import de.brands4friends.daleq.integration.tables.HsqldbAssertTableTable;

@Configuration
@Profile("HSQLDB")
public class HsqldbConfig implements DbConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().addScript("schema-hsqldb.sql").build();
    }

    @Bean
    public IDataTypeFactory dataTypeFactory() {
        return new HsqldbDataTypeFactory();
    }

    @Bean
    public TableProvider allTypesProvider() {
        return new TableProvider(HsqldbAllTypesTable.class, HsqldbAssertTableTable.class, HsqldbAssertTableTable.ID);
    }
}
