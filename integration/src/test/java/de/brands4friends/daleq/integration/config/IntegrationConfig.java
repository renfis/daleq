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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import de.brands4friends.daleq.integration.rules.RestrictDbRule;
import de.brands4friends.daleq.spring.DaleqSupportBean;

@Configuration
public class IntegrationConfig {

    @Autowired
    private DbConfig dbConfig;

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dbConfig.dataSource());
    }

    @Bean
    public DaleqSupportBean daleqSupport() {
        final DaleqSupportBean daleqSupportBean = new DaleqSupportBean();
        daleqSupportBean.setDataTypeFactory(dbConfig.dataTypeFactory());
        daleqSupportBean.setDataSource(dbConfig.dataSource());
        return daleqSupportBean;
    }

    @Bean
    public RestrictDbRule restrictDbRule() {
        return new RestrictDbRule(dbConfig.currentDb());
    }
}
