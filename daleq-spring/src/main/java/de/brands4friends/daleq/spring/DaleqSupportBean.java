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

import javax.sql.DataSource;

import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Preconditions;

import de.brands4friends.daleq.core.DaleqSupport;
import de.brands4friends.daleq.core.internal.dbunit.Asserter;
import de.brands4friends.daleq.core.internal.dbunit.DbUnitDaleqSupport;
import de.brands4friends.daleq.core.internal.dbunit.IDataSetFactory;
import de.brands4friends.daleq.core.internal.dbunit.dataset.InMemoryDataSetFactory;

/**
 * Creates a DaleqSupport Spring Bean.
 */
public class DaleqSupportBean implements FactoryBean<DaleqSupport>, InitializingBean {

    private IDataSetFactory dataSetFactory = new InMemoryDataSetFactory();
    private IDataTypeFactory dataTypeFactory = new DefaultDataTypeFactory();

    private DataSource dataSource;

    private DbUnitDaleqSupport daleqSupport;

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataTypeFactory(final IDataTypeFactory dataTypeFactory) {
        this.dataTypeFactory = dataTypeFactory;
    }

    public void setDataSetFactory(final IDataSetFactory dataSetFactory) {
        this.dataSetFactory = dataSetFactory;
    }

    @Override
    public void afterPropertiesSet() {
        Preconditions.checkNotNull(dataSource, "dataSource");

        final SpringConnectionFactory connectionFactory = new SpringConnectionFactory();
        connectionFactory.setDataTypeFactory(dataTypeFactory);
        connectionFactory.setDataSource(dataSource);

        final Asserter asserter = new Asserter(dataSetFactory, connectionFactory);

        daleqSupport = new DbUnitDaleqSupport(dataSetFactory, connectionFactory, asserter);
    }

    @Override
    public DaleqSupport getObject() {
        return daleqSupport;
    }

    @Override
    public Class<?> getObjectType() {
        return DaleqSupport.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
