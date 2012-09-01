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

import org.junit.Assume;

/**
 * Decides whether a DB integration test should be performed.
 */
public class RunDbBarrier {

    private final boolean shouldRun;

    public RunDbBarrier(final boolean shouldRun) {
        this.shouldRun = shouldRun;
    }

    public void checkShouldRun() {
        Assume.assumeTrue(shouldRun);
    }
}
