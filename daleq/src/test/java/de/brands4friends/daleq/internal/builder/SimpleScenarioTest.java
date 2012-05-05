package de.brands4friends.daleq.internal.builder;

import static de.brands4friends.daleq.Daleq.aRow;
import static de.brands4friends.daleq.Daleq.aTable;
import static de.brands4friends.daleq.PropertyDef.pd;
import static de.brands4friends.daleq.internal.builder.SimpleScenarioTest.MyTable.NAME;
import static de.brands4friends.daleq.internal.builder.SimpleScenarioTest.MyTable.VALUE;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.Table;

public class SimpleScenarioTest {

    @de.brands4friends.daleq.TableDef("foo")
    public final static class MyTable {
        public static final PropertyDef ID       = pd(DataType.INTEGER);
        public static final PropertyDef NAME     = pd(DataType.VARCHAR);
        public static final PropertyDef VALUE    = pd(DataType.BIT);
        public static final PropertyDef MODIFIED = pd(DataType.TIMESTAMP);
    }

    @Test
    public void justTwoRows() {
        Table table = aTable(MyTable.class).with(aRow(1),aRow(2));
    }

    @Test
    public void rowsWithProperties() {
        Table table = aTable(MyTable.class)
                .with(
                        aRow(42).p(NAME, "foo").p(VALUE, "1"),
                        aRow(23).p(NAME, "bar")
                );
    }
}
