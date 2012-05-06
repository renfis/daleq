package de.brands4friends.daleq;

import de.brands4friends.daleq.internal.builder.Context;
import de.brands4friends.daleq.internal.container.RowContainer;
import de.brands4friends.daleq.internal.structure.TableStructure;

public interface Row {

    public Row p(PropertyDef propertyDef, Object value);
    RowContainer build(Context context, final TableStructure tableStructure);
}
