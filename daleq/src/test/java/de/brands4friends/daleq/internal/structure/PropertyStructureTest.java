package de.brands4friends.daleq.internal.structure;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class PropertyStructureTest {

    @Test
    public void testHashCodeAndEquals(){
        EqualsAssert.assertProperEqualsAndHashcode(PropertyStructure.class);
    }
}
