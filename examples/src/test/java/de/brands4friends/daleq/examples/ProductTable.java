package de.brands4friends.daleq.examples;

import org.dbunit.dataset.datatype.DataType;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TableDef;

@TableDef("PRODUCT")
public class ProductTable {
    public static final FieldDef ID    = FieldDef.fd(DataType.INTEGER);
    public static final FieldDef NAME  = FieldDef.fd(DataType.VARCHAR);
    public static final FieldDef SIZE  = FieldDef.fd(DataType.VARCHAR);
    public static final FieldDef PRICE = FieldDef.fd(DataType.DECIMAL);
}
