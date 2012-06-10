package de.brands4friends.daleq;

import de.brands4friends.daleq.internal.types.TableType;

public interface Row {

    Row f(FieldDef fieldDef, Object value);

    RowContainer build(Context context, final TableType tableType);
}
