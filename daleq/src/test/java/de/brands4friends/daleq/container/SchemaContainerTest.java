package de.brands4friends.daleq.container;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class SchemaContainerTest {

    @Test
    public void testHashCodeAndEquals() {
        EqualsAssert.assertProperEqualsAndHashcode(SchemaContainerImpl.class);
    }
}
