package de.brands4friends.daleq;

import org.dbunit.dataset.datatype.DataType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class FieldDefTest{

    @Test
    public void testHashCodeAndEquals(){
        EqualsVerifier.forClass(FieldDef.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void aPropertyWithName_should_haveName(){
        Assert.assertThat(FieldDef.fd(DataType.BIGINT).name("foo").hasName(), Matchers.is(true));
    }

    @Test
    public void aPropertyWithoutName_should_haveName(){
        Assert.assertThat(FieldDef.fd(DataType.BIGINT).hasName(), Matchers.is(false));
    }
}
