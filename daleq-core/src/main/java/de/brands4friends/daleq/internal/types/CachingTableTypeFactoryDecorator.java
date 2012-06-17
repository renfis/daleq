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

package de.brands4friends.daleq.internal.types;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class CachingTableTypeFactoryDecorator implements TableTypeFactory {

    private final TableTypeFactory delegate;
    private final Map<String, TableType> cache;

    public CachingTableTypeFactoryDecorator(final TableTypeFactory delegate) {
        this.delegate = delegate;
        this.cache = Maps.newHashMap();
    }

    @Override
    public <T> TableType create(final Class<T> fromClass) {
        Preconditions.checkNotNull(fromClass);
        final String className = fromClass.getCanonicalName();
        if (cache.containsKey(className)) {
            return cache.get(className);
        }

        final TableType tableType = delegate.create(fromClass);
        cache.put(className, tableType);
        return tableType;
    }
}
