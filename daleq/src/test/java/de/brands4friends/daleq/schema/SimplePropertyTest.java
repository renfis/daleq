package de.brands4friends.daleq.schema;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.easymock.EasyMockSupport;
import org.junit.Test;

import de.brands4friends.daleq.PropertyWriter;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 *
 */
public class SimplePropertyTest extends EasyMockSupport {

    @Test
    public void creation_should_haveNameAndValue(){
        Property p = new SimpleProperty(new PropertyType("a", DataType.VARCHAR),"b");
        assertThat(p.getName(),is("a"));
        assertThat(p.getValue(),is((Object) "b"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void creationWithNullName_should_fail(){
        new SimpleProperty(null,"b");
    }

    @Test
    public void creationWithNullValue_should_notFail(){
        assertThat(
                new SimpleProperty( new PropertyType("a", DataType.VARCHAR ), null).getValue(),
                is(nullValue()));
    }

    @Test
    public void creationWithNonString_should_haveThatObjectAsValue(){
        assertThat(
                new SimpleProperty(new PropertyType("type",DataType.INTEGER),1234l).getValue(),
                instanceOf(Long.class));
    }

    @Test
    public void write_should_writeToWriter(){
        final PropertyWriter writer = createMock(PropertyWriter.class);
        final String name = "foo";
        final String val = "val";
        writer.writeMapped(name,val);

        replayAll();
        final SimpleProperty prop = new SimpleProperty(Daleq.pt(name, DataType.VARCHAR),val);
        prop.write(writer);
        verifyAll();
    }

    @Test
    public void testHashCodeAndEquals(){
        EqualsVerifier.forClass(SimpleProperty.class).verify();
    }
}
