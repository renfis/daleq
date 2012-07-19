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

package de.brands4friends.daleq.core.internal.types;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.core.DaleqBuildException;
import de.brands4friends.daleq.core.TableType;
import de.brands4friends.daleq.core.TableTypeReference;

@ThreadSafe
public class CachingTableTypeRepository implements TableTypeRepository {

    private final Cache<TableTypeReference, TableType> cache;
    private final List<TableTypeResolver> resolvers;

    public CachingTableTypeRepository() {
        this.resolvers = new CopyOnWriteArrayList<TableTypeResolver>(Lists.newArrayList(
                new ClassBasedTableTypeResolver()
        ));
        this.cache = CacheBuilder.newBuilder().build();
    }

    @Override
    public TableType get(final TableTypeReference tableRef) {
        final TableType tableType = cache.getIfPresent(tableRef);
        if (tableType != null) {
            return tableType;
        }

        final TableType resolved = doGet(tableRef);
        cache.put(tableRef, resolved);
        return resolved;
    }

    private TableType doGet(final TableTypeReference tableRef) {
        final Optional<TableTypeResolver> resolver = Iterables.tryFind(resolvers, new Predicate<TableTypeResolver>() {
            @Override
            public boolean apply(@Nullable final TableTypeResolver resolver) {
                if (resolver == null) {
                    return false;
                }
                return resolver.canResolve(tableRef);
            }
        });
        if (!resolver.isPresent()) {
            throw new DaleqBuildException("No TableTypeResolver registered for " + tableRef);
        }

        return resolver.get().resolve(tableRef);
    }
}
