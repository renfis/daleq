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

package de.brands4friends.daleq.integration.rules;


import java.util.Set;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

import de.brands4friends.daleq.integration.config.SupportedDb;

/**
 * Ignores a SupportedDb on a particular test
 */
public class RestrictDbRule implements TestRule {

    private final Logger logger = LoggerFactory.getLogger(RestrictDbRule.class);

    private final SupportedDb currentDb;

    public RestrictDbRule(final SupportedDb currentDb) {
        this.currentDb = currentDb;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {

        final Restrict restrict = description.getAnnotation(Restrict.class);
        if (restrict != null) {
            final Set<SupportedDb> ignoredDbs = Sets.newHashSet(restrict.not());
            if (ignoredDbs.contains(currentDb)) {
                logger.info("Test {} will be ignored. {}",
                        new Object[]{description.getDisplayName(), restrict.reason()}
                );
                return new Noop();
            }
        }

        return base;
    }
}
