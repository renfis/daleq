package de.brands4friends.daleq.integration.tests.assertingtable;

import org.dbunit.dataset.datatype.DataType;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TableDef;

@TableDef("ASSERT_TABLE")
public class AssertTableTable {
    public static final FieldDef ID = FieldDef.fd(DataType.INTEGER);
    public static final FieldDef NAME = FieldDef.fd(DataType.VARCHAR);
    public static final FieldDef AMOUNT = FieldDef.fd(DataType.DECIMAL);
}
