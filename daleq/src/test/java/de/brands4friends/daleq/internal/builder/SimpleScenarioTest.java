package de.brands4friends.daleq.internal.builder;

import static de.brands4friends.daleq.Daleq.aRow;
import static de.brands4friends.daleq.Daleq.aTable;
import static de.brands4friends.daleq.internal.builder.SimpleScenarioTest.MyTable.NAME;
import static de.brands4friends.daleq.internal.builder.SimpleScenarioTest.MyTable.VALUE;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import de.brands4friends.daleq.FieldDef;

public class SimpleScenarioTest {

    @de.brands4friends.daleq.TableDef("foo")
    public final static class MyTable {
        public static final FieldDef ID       = FieldDef.fd(DataType.INTEGER);
        public static final FieldDef NAME     = FieldDef.fd(DataType.VARCHAR);
        public static final FieldDef VALUE    = FieldDef.fd(DataType.BIT);
        public static final FieldDef MODIFIED = FieldDef.fd(DataType.TIMESTAMP);
    }

    @Test
    public void justTwoRows() {
        aTable(MyTable.class).with(aRow(1),aRow(2));
    }

    @Test
    public void rowsWithProperties() {
        aTable(MyTable.class)
                .with(
                        aRow(42).f(NAME, "foo").f(VALUE, "1"),
                        aRow(23).f(NAME, "bar")
                );
    }
}
