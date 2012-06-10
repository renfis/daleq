package de.brands4friends.daleq;

import javax.annotation.Nullable;

import com.sun.istack.internal.NotNull;

import de.brands4friends.daleq.internal.types.TableType;

public interface Row {

    Row f(@NotNull FieldDef fieldDef, @Nullable Object value);

    RowContainer build(@NotNull Context context, @NotNull final TableType tableType);
}
