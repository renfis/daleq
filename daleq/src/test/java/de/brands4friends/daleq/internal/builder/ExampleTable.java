package de.brands4friends.daleq.internal.builder;

import org.dbunit.dataset.datatype.DataType;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TableDef;

@TableDef("FOO")
public final class ExampleTable {
    public static final FieldDef PROP_A = FieldDef.fd(DataType.INTEGER);
    public static final FieldDef PROP_B = FieldDef.fd(DataType.INTEGER);
}
