package de.brands4friends.daleq.internal.structure;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class SchemaStructureTest {

    @Test
    public void testHashcodeAndEquals() {
        EqualsAssert.assertProperEqualsAndHashcode(SchemaStructure.class);
    }
}
