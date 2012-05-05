package de.brands4friends.daleq;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class PropertyDefTest {

    @Test
    public void testHashCodeAndEquals(){
        EqualsAssert.assertProperEqualsAndHashcode(PropertyDef.class);
    }
}
