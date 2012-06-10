package de.brands4friends.daleq.integration.tests.alltypes;

import static de.brands4friends.daleq.FieldDef.fd;

import org.dbunit.dataset.datatype.DataType;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TableDef;

@TableDef("ALL_TYPES")
public class AllTypesTable {
    // Characters
    public static final FieldDef A_VARCHAR = fd(DataType.VARCHAR);
    public static final FieldDef A_CHAR = fd(DataType.CHAR);
    public static final FieldDef A_LONGVARCHAR = fd(DataType.LONGVARCHAR);
    public static final FieldDef A_NVARCHAR = fd(DataType.NVARCHAR);
    public static final FieldDef A_CLOB = fd(DataType.CLOB);

    // Numerics
    public static final FieldDef A_NUMERIC = fd(DataType.NUMERIC);
    public static final FieldDef A_DECIMAL = fd(DataType.DECIMAL);
    public static final FieldDef A_BOOLEAN = fd(DataType.BOOLEAN);
    public static final FieldDef A_BIT = fd(DataType.BIT);
    public static final FieldDef A_INTEGER = fd(DataType.INTEGER);
    public static final FieldDef A_TINYINT = fd(DataType.TINYINT);
    public static final FieldDef A_SMALLINT = fd(DataType.SMALLINT);
    public static final FieldDef A_BIGINT = fd(DataType.BIGINT);
    public static final FieldDef A_REAL = fd(DataType.REAL);
    public static final FieldDef A_DOUBLE = fd(DataType.DOUBLE);
    public static final FieldDef A_FLOAT = fd(DataType.FLOAT);

    // Dates
    public static final FieldDef A_DATE = fd(DataType.DATE);
    //    public static final FieldDef A_TIME = fd(DataType.TIME);
    public static final FieldDef A_TIMESTAMP = fd(DataType.TIMESTAMP);

    // Binaries
    public static final FieldDef A_VARBINARY = fd(DataType.VARBINARY);
    //    public static final FieldDef A_BINARY = fd(DataType.BINARY);
    public static final FieldDef A_LONGVARBINARY = fd(DataType.LONGVARBINARY);
    public static final FieldDef A_BLOB = fd(DataType.BLOB);
}
