package de.brands4friends.daleq;

import org.dbunit.dataset.datatype.DataType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class PropertyDefTest {

    @Test
    public void testHashCodeAndEquals(){
        EqualsAssert.assertProperEqualsAndHashcode(PropertyDef.class);
    }

    @Test
    public void aPropertyWithName_should_haveName(){
        Assert.assertThat(PropertyDef.pd("foo", DataType.BIGINT).hasName(), Matchers.is(true));
    }

    @Test
    public void aPropertyWithoutName_should_haveName(){
        Assert.assertThat(PropertyDef.pd(DataType.BIGINT).hasName(), Matchers.is(false));
    }
}
