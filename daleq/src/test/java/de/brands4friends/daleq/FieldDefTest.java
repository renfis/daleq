package de.brands4friends.daleq;

import org.dbunit.dataset.datatype.DataType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class FieldDefTest{

    @Test
    public void testHashCodeAndEquals(){
        EqualsAssert.assertProperEqualsAndHashcode(FieldDef.class);
    }

    @Test
    public void aPropertyWithName_should_haveName(){
        Assert.assertThat(FieldDef.fd("foo", DataType.BIGINT).hasName(), Matchers.is(true));
    }

    @Test
    public void aPropertyWithoutName_should_haveName(){
        Assert.assertThat(FieldDef.fd(DataType.BIGINT).hasName(), Matchers.is(false));
    }
}
