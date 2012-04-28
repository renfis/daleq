package de.brands4friends.daleq.schema;

import static de.brands4friends.daleq.schema.Daleq.p;
import static de.brands4friends.daleq.schema.Daleq.row;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 *
 */
public class SimpleRowTest {

    @Test
    public void testAdd(){
        assertThat(
                row(p("a", DataType.VARCHAR, "b")).add(p("c", DataType.VARCHAR, "d")).add(p("e", DataType.VARCHAR, "f")),
                is(row(p("a", DataType.VARCHAR, "b"),p("c", DataType.VARCHAR, "d"),p("e", DataType.VARCHAR, "f")))
        );
    }

    @Test
    public void testWith(){
        assertThat(
                row(p("a", DataType.VARCHAR, "b"), p("c", DataType.VARCHAR, "d")).with(p("c", DataType.VARCHAR, "X")),
                is(row(p("a", DataType.VARCHAR, "b"),p("c", DataType.VARCHAR, "X"))));
    }

    @Test
    public void testDuplicate(){
        Row original = row(p("a", DataType.VARCHAR, "b"));
        Row duplicate = original.duplicate();

        assertThat(duplicate, is(original));
        assertThat(duplicate, not(sameInstance(original)));
    }

    @Test
    public void testWithout(){
        Row original = row(p("a", DataType.VARCHAR, "1"),p("b", DataType.VARCHAR, "2"));
        Row without  = original.without("b");
        Row expected = row(p("a", DataType.VARCHAR, "1"));

        assertThat(without, is(expected));
        assertThat(without, not(sameInstance(original)));
    }

    @Test
    public void testWithoutUnknownColumn(){
        Row original = row(p("a", DataType.VARCHAR, "1"),p("c", DataType.VARCHAR, "2"));
        Row without  = original.without("b");
        Row expected = row(p("a", DataType.VARCHAR, "1"),p("c", DataType.VARCHAR, "2"));

        assertThat(without, is(expected));
        assertThat(without, not(sameInstance(original)));
    }

    @Test
    public void testHashCodeAndEquals(){
        EqualsVerifier.forClass(SimpleRow.class).verify();
    }
}
