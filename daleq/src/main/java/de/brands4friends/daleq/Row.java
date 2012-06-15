package de.brands4friends.daleq;

import javax.annotation.Nullable;

import de.brands4friends.daleq.internal.types.TableType;

public interface Row {

    Row f(FieldDef fieldDef, @Nullable Object value);

    RowContainer build(Context context, final TableType tableType);
}
