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

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.brands4friends.daleq.core.DaleqSupport;
import de.brands4friends.daleq.integration.config.H2Config;
import de.brands4friends.daleq.integration.config.HsqldbConfig;
import de.brands4friends.daleq.integration.config.IntegrationConfig;
import de.brands4friends.daleq.integration.config.MysqlConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        IntegrationConfig.class,
        HsqldbConfig.class,
        H2Config.class,
        MysqlConfig.class
})
@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    protected DaleqSupport daleq;

}
