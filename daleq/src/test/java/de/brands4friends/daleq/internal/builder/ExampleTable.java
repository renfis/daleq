package de.brands4friends.daleq.internal.builder;

import org.dbunit.dataset.datatype.DataType;

import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.TableDef;

@TableDef("FOO")
public final class ExampleTable {
    public static final PropertyDef PROP_A = PropertyDef.pd(DataType.INTEGER);
    public static final PropertyDef PROP_B = PropertyDef.pd(DataType.INTEGER);
}
