package de.brands4friends.daleq.internal.builder;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class SchemaContainerImplTest {

    @Test
    public void testHashCodeAndEquals() {
        EqualsAssert.assertProperEqualsAndHashcode(SchemaContainerImpl.class);
    }
}
