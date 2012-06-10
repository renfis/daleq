package de.brands4friends.daleq.internal.types;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class SchemaTypeTest {

    @Test
    public void testHashcodeAndEquals() {
        EqualsAssert.assertProperEqualsAndHashcode(SchemaType.class);
    }
}
