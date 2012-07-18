package de.brands4friends.daleq.core.internal.types;

import de.brands4friends.daleq.core.TableType;
import de.brands4friends.daleq.core.TableTypeReference;

/**
 *
 */
public class ClassBasedTableTypeResolver implements TableTypeResolver {

    private final TableTypeFactory tableTypeFactory;

    public ClassBasedTableTypeResolver() {
        this.tableTypeFactory = new TableTypeFactory();
    }

    @Override
    public boolean canResolve(final TableTypeReference reference) {
        return reference instanceof ClassBasedTableTypeReference;
    }

    @Override
    public TableType resolve(final TableTypeReference reference) {
        final ClassBasedTableTypeReference<?> classBased = asClassBased(reference);
        return tableTypeFactory.create(classBased.getTable());
    }

    private ClassBasedTableTypeReference<?> asClassBased(final TableTypeReference reference) {
        if (!canResolve(reference)) {
            throw new IllegalArgumentException(
                    "The given TableTypeReference could be resolved by the ClassBasedTableTypeResolver.");
        }
        return (ClassBasedTableTypeReference<?>) reference;
    }
}
