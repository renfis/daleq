package de.brands4friends.daleq.internal.structure;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.test.EqualsAssert;

public class TableStructureTest {

    @Test
    public void testHashcodeAndEquals() {
        EqualsAssert.assertProperEqualsAndHashcode(TableStructure.class);
    }

    @Test
    public void findStructureByDefOfExisting_should_returnStructure() {
        final DataType integer = DataType.INTEGER;
        final FieldDef origin = new FieldDef("propertyDef", integer);
        final FieldStructure fieldStructure = new FieldStructure(
                "P NAME",
                integer,
                new SubstitutingTemplateValue("bar"),
                origin
        );
        final TableStructure table =
                new TableStructure("SOME_NAME",
                        fieldStructure);

        assertThat(table.findStructureByDef(origin), is(fieldStructure));
    }

    @Test
    public void findStructureByDefOfNonExisting_should_notReturnStructure() {
        final FieldDef origin = new FieldDef("propertyDef", DataType.INTEGER);
        final TableStructure table = new TableStructure("SOME_NAME");
        assertThat(table.findStructureByDef(origin), is(nullValue()));
    }
}
