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

package de.brands4friends.daleq.core;

/**
 * Provides access to contextual information needed while building a {@link Table}.
 */
public interface Context {
    /**
     * A service lookup.
     * <p>
     * The <code>Context</code> holds a service registry. Building a table requires access all of these services.
     * This method decouples the public API from exposing the actual services. Which services are registered
     * is implementation depending and my change.
     *
     * @param <T> The type of service, that should be looked up
     * @param service the class of a requested service.
     * @return If the <code>Context</code> has a registered implementation for requested <code>servive</code>, the
     *         implementation is returned, otherwise an exception is thrown.
     * @throws IllegalArgumentException if the <code>Context</code> does not have the requested
     *                                  <code>service</code>.
     */
    <T> T getService(Class<T> service);
}
