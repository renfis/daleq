package de.brands4friends.daleq.internal.types;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.internal.template.StringTemplateValue;
import de.brands4friends.daleq.test.EqualsAssert;

public class TableTypeTest {

    @Test
    public void testHashcodeAndEquals() {
        EqualsAssert.assertProperEqualsAndHashcode(TableType.class);
    }

    @Test
    public void findStructureByDefOfExisting_should_returnStructure() {
        final DataType integer = DataType.INTEGER;
        final FieldDef origin = new FieldDef("propertyDef", integer, null);
        final FieldType fieldType = new FieldType(
                "P NAME",
                integer,
                new StringTemplateValue("bar"),
                origin
        );
        final TableType table =
                new TableType("SOME_NAME",
                        fieldType);

        assertThat(table.findStructureByDef(origin), is(fieldType));
    }

    @Test
    public void findStructureByDefOfNonExisting_should_notReturnStructure() {
        final FieldDef origin = new FieldDef("propertyDef", DataType.INTEGER, null);
        final TableType table = new TableType("SOME_NAME");
        assertThat(table.findStructureByDef(origin), is(nullValue()));
    }
}
