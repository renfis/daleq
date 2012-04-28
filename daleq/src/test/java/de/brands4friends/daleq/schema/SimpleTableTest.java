package de.brands4friends.daleq.schema;

import static de.brands4friends.daleq.common.Range.range;
import static de.brands4friends.daleq.schema.Daleq.p;
import static de.brands4friends.daleq.schema.Daleq.pt;
import static de.brands4friends.daleq.schema.Daleq.row;
import static de.brands4friends.daleq.schema.Daleq.table;
import static de.brands4friends.daleq.schema.Daleq.template;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import com.google.common.collect.Lists;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 *
 */
public class SimpleTableTest {

    public static final PropertyType AGE_TYPE = new PropertyType("age", DataType.VARCHAR);
    public static final PropertyType NAME_TYPE = pt("name", DataType.VARCHAR);

    @Test
    public void testAdd() {
        Table actual =
                table("ppl",
                        row(NAME_TYPE.of("Name 0")),
                        row(NAME_TYPE.of("Name 1")),
                        row(NAME_TYPE.of("Name 2"))
                );
        actual.add(AGE_TYPE.of("12"));

        Table expected =
                table("ppl",
                        row(NAME_TYPE.of("Name 0"), AGE_TYPE.of("12")),
                        row(NAME_TYPE.of("Name 1"), AGE_TYPE.of("12")),
                        row(NAME_TYPE.of("Name 2"), AGE_TYPE.of("12"))
                );
        assertEquals(expected, actual);
    }

    @Test
    public void testAddOnIterations(){

        Table actual =
                table("ppl",
                        row(NAME_TYPE.of("Name 0")),
                        row(NAME_TYPE.of("Name 1")),
                        row(NAME_TYPE.of("Name 2"))
                );
        actual.add(AGE_TYPE, Lists.<String>newArrayList("12","13","14"));

        Table expected =
                table("ppl",
                        row(p(pt("name",DataType.VARCHAR), "Name 0"), AGE_TYPE.of("12")),
                        row(p(pt("name",DataType.VARCHAR), "Name 1"), AGE_TYPE.of("13")),
                        row(p(pt("name",DataType.VARCHAR), "Name 2"), AGE_TYPE.of("14"))
                );
        assertEquals(expected, actual);
    }

    @Test
    public void testJoin(){

        PropertyType foo_id = pt("foo_id", DataType.INTEGER);
        PropertyType fk_foo_id = pt("fk_foo_id", DataType.INTEGER);

        Table foos = template(p("foo_id", DataType.INTEGER, "${_}")).toTable("foos",range(5));
        Table bars = template(p("bar_id", DataType.INTEGER, "${_}")).toTable("bars",range(5,10));
        bars.join(foos,foo_id,fk_foo_id);

        Table expectedFoos = table("foos",
                row(p("foo_id", DataType.INTEGER, "0")),
                row(p("foo_id", DataType.INTEGER, "1")),
                row(p("foo_id", DataType.INTEGER, "2")),
                row(p("foo_id", DataType.INTEGER, "3")),
                row(p("foo_id", DataType.INTEGER, "4"))
        );

        Table expectedBars = table("bars",
                row(p("bar_id", DataType.INTEGER, "5"),p("fk_foo_id", DataType.INTEGER, "0")),
                row(p("bar_id", DataType.INTEGER, "6"),p("fk_foo_id", DataType.INTEGER, "1")),
                row(p("bar_id", DataType.INTEGER, "7"),p("fk_foo_id", DataType.INTEGER, "2")),
                row(p("bar_id", DataType.INTEGER, "8"),p("fk_foo_id", DataType.INTEGER, "3")),
                row(p("bar_id", DataType.INTEGER, "9"),p("fk_foo_id", DataType.INTEGER, "4"))
        );
        assertThat(foos, is(expectedFoos));
        assertThat(bars, is(expectedBars));
    }

    @Test
    public void testConcatOfIterables(){
        Table foos = table("foos",row(p("a", DataType.VARCHAR, "b")));
        Table bars = table("bars",row(p("c", DataType.VARCHAR, "d")));
        foos.concat(bars);

        Table expected = table("foos",row(p("a", DataType.VARCHAR, "b")),row(p("c", DataType.VARCHAR, "d")));
        assertEquals(expected,foos);
    }

    @Test
    public void testConcatOfRows(){
        Table foos = table("foos",row(p("a", DataType.VARCHAR, "b")));
        foos.concat(row(p("c", DataType.VARCHAR, "d")));

        Table expected = table("foos",row(p("a", DataType.VARCHAR, "b")),row(p("c", DataType.VARCHAR, "d")));
        assertEquals(expected,foos);
    }

    @Test
    public void testWithout(){
        Table original = table("foo",
                row(p("a", DataType.VARCHAR, "1"),p("b", DataType.VARCHAR, "1"),p("c", DataType.VARCHAR, "1")),
                row(p("a", DataType.VARCHAR, "2"),p("b", DataType.VARCHAR, "2"),p("c", DataType.VARCHAR, "2")),
                row(p("a", DataType.VARCHAR, "3"),p("b", DataType.VARCHAR, "3"),p("c", DataType.VARCHAR, "3"))
            );

        Table without = original.without("b","c");
        Table expected = table("foo",
                row(p("a", DataType.VARCHAR, "1")),
                row(p("a", DataType.VARCHAR, "2")),
                row(p("a", DataType.VARCHAR, "3"))
            );

        assertEquals(expected,without);
        assertNotSame(original,without);
    }

    @Test
    public void testWithoutNonExistingRow(){
        Table original = table("foo",
                row(p("a", DataType.VARCHAR, "1"),p("b", DataType.VARCHAR, "1"),p("c", DataType.VARCHAR, "1")),
                row(p("a", DataType.VARCHAR, "2"),p("b", DataType.VARCHAR, "2"),p("c", DataType.VARCHAR, "2")),
                row(p("a", DataType.VARCHAR, "3"),p("b", DataType.VARCHAR, "3"),p("c", DataType.VARCHAR, "3"))
            );

        Table without = original.without("d");

        Table expected = table("foo",
                row(p("a", DataType.VARCHAR, "1"),p("b", DataType.VARCHAR, "1"),p("c", DataType.VARCHAR, "1")),
                row(p("a", DataType.VARCHAR, "2"),p("b", DataType.VARCHAR, "2"),p("c", DataType.VARCHAR, "2")),
                row(p("a", DataType.VARCHAR, "3"),p("b", DataType.VARCHAR, "3"),p("c", DataType.VARCHAR, "3"))
            );

        assertEquals(expected,without);
    }

    @Test
    public void testDuplicate(){
        Table original = table("foo",row(p("a", DataType.VARCHAR, "b")));
        Table duplicate = original.duplicate();

        assertEquals(original,duplicate);
        assertNotSame(original,duplicate);
        assertNotSame(original.getRows(),duplicate.getRows());
    }

    @Test
    public void testHashCodeAndEquals(){
        EqualsVerifier
                .forClass(SimpleTable.class)
                .verify();
    }

}
