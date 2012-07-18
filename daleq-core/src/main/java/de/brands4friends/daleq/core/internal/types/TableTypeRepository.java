package de.brands4friends.daleq.core.internal.types;

import de.brands4friends.daleq.core.TableType;
import de.brands4friends.daleq.core.TableTypeReference;

/**
 *
 */
public interface TableTypeRepository {
    
    TableType get(TableTypeReference tableRef);
}
