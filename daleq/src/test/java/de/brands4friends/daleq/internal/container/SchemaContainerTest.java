package de.brands4friends.daleq.internal.container;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class SchemaContainerTest {

    @Test
    public void testHashCodeAndEquals() {
        EqualsAssert.assertProperEqualsAndHashcode(SchemaContainer.class);
    }
}
