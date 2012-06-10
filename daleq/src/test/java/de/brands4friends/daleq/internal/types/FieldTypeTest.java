package de.brands4friends.daleq.internal.types;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class FieldTypeTest {

    @Test
    public void testHashCodeAndEquals() {
        EqualsAssert.assertProperEqualsAndHashcode(FieldType.class);
    }
}
