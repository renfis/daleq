package de.brands4friends.daleq.examples;

import static de.brands4friends.daleq.PropertyDef.pd;

import org.dbunit.dataset.datatype.DataType;

import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.TableDef;

@TableDef("PRODUCT")
public class ProductTable {
    public static final PropertyDef ID    = pd(DataType.INTEGER);
    public static final PropertyDef NAME  = pd(DataType.VARCHAR);
    public static final PropertyDef SIZE  = pd(DataType.VARCHAR);
    public static final PropertyDef PRICE = pd(DataType.DECIMAL);
}
