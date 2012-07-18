package de.brands4friends.daleq.core.internal.types;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.brands4friends.daleq.core.DaleqBuildException;
import de.brands4friends.daleq.core.TableType;
import de.brands4friends.daleq.core.TableTypeReference;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
