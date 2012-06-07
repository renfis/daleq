package de.brands4friends.daleq.internal.builder;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class FieldHolderTest{

    @Test
    public void testHashCodeAndEquals(){
        EqualsAssert.assertProperEqualsAndHashcode(FieldHolder.class);
    }
}
