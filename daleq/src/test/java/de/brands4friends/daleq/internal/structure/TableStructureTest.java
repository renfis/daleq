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
    public void testHashcodeAndEquals(){
        EqualsAssert.assertProperEqualsAndHashcode(TableStructure.class);
    }

    @Test
    public void findStructureByDefOfExisting_should_returnStructure(){
        DataType integer = DataType.INTEGER;
        FieldDef origin = new FieldDef("propertyDef",integer);
        PropertyStructure propertyStructure = new PropertyStructure("P NAME", integer, new TemplateValue("bar"), origin);
        final TableStructure table =
                new TableStructure("SOME_NAME",
                        propertyStructure);

        assertThat(table.findStructureByDef(origin), is(propertyStructure));
    }

    @Test
    public void findStructureByDefOfNonExisting_should_notReturnStructure(){
        FieldDef origin = new FieldDef("propertyDef", DataType.INTEGER);
        final TableStructure table = new TableStructure("SOME_NAME");
        assertThat(table.findStructureByDef(origin), is(nullValue()));
    }
}
