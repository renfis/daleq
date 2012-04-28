package de.brands4friends.daleq.schema;

import static de.brands4friends.daleq.schema.Daleq.p;
import static de.brands4friends.daleq.schema.Daleq.row;
import static de.brands4friends.daleq.schema.Daleq.table;
import static de.brands4friends.daleq.schema.Daleq.template;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.dbunit.dataset.datatype.DataType;
import org.hamcrest.Matchers;
import org.junit.Test;

import com.google.common.collect.Lists;

import nl.jqno.equalsverifier.EqualsVerifier;

public class SimpleTemplateTest {
    @Test
    public void testToTable(){
        List<Integer> range = Lists.newArrayList(0, 1, 2);
        Table actual =
                Daleq.template(
                        p("name", DataType.VARCHAR, "Name_${i}"),
                        p("age", DataType.VARCHAR, "${i}")
                ).toTable("ppl",range,"i");

        Table expected =
                table("ppl",
                    row(p("name", DataType.VARCHAR, "Name_0"),p("age", DataType.VARCHAR, "0")),
                    row(p("name", DataType.VARCHAR, "Name_1"),p("age", DataType.VARCHAR, "1")),
                    row(p("name", DataType.VARCHAR, "Name_2"),p("age", DataType.VARCHAR, "2"))
                );

        assertThat(actual, Matchers.is(expected));
    }

    @Test
    public void testToRow(){
        Template template = new SimpleTemplate(p("a", DataType.VARCHAR, "${i}"));
        Row actual = template.toRow(1,"i");
        Row expected = row(p("a", DataType.VARCHAR, "1"));
        assertEquals(expected,actual);
    }

    @Test
    public void testToRowWithDefault(){
        Template template = new SimpleTemplate(p("a", DataType.VARCHAR, "${_}"));
        Row actual = template.toRow(1);
        Row expected = row(p("a", DataType.VARCHAR, "1"));
        assertEquals(expected,actual);
    }

    @Test
    public void substitute_should_replaceVariable(){

        assertSubstitutedRow(
                row(p("name", DataType.VARCHAR, "My name is Foo"),p("age", DataType.VARCHAR, "Foo")),
                template(p("name", DataType.VARCHAR, "My name is ${i}"), p("age", DataType.VARCHAR, "${i}")),
                "i", "Foo"
        );
    }

    @Test
    public void incompleteSubstitute_should_keepString(){
        assertSubstitutedRow(
                row(p("name", DataType.VARCHAR, "My name is ${i")),
                template(p("name", DataType.VARCHAR, "My name is ${i")),
                "i","Foo"
        );
    }

    @Test
    public void escapedSubstitute_should_returnUnescapedString(){
        assertSubstitutedRow(
                row(p("name", DataType.VARCHAR, "My name is ${i}")),
                template(p("name", DataType.VARCHAR, "My name is $${i}")),
                "i","Foo"
        );
    }

    @Test
    public void unknownVariable_should_notBeSubstituted(){
        assertSubstitutedRow(
                row(p("name", DataType.VARCHAR, "My ${foo} is bar")),
                template(p("name", DataType.VARCHAR, "My ${foo} is ${i}")),
                "i","bar"
        );
    }

    @Test
    public void aNoneStringValue_should_notBeSubstituted(){
        final Object val = new Object(){
            @Override
            public String toString() {
                return "i should not be a String";
            }
        };
        final Row row =  template(p("foo", DataType.VARCHAR, val)).toRow("bar");
        assertThat(row.get("foo").getValue(), sameInstance(val));
    }

    @Test
    public void aStringWithoutVariable_should_keepTheInstanceInToRow() {
        final String str = new StringBuilder().append("foo").append(1).toString();
        assertThat(
                (String) template(p("foo", DataType.VARCHAR, str)).toRow("bar").get("foo").getValue(),
                sameInstance(str)
        );
    }

    @Test
    public void testHashCodeAndEquals(){
        EqualsVerifier.forClass(SimpleTemplate.class).verify();
    }

    private void assertSubstitutedRow(Row expectedRow, Template toBeSubstituted, String varName, String varValue) {
        assertEquals(expectedRow,toBeSubstituted.toRow(varValue, varName));
    }

}
