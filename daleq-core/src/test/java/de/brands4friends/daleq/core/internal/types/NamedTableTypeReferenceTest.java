package de.brands4friends.daleq.core.internal.types;

import org.junit.Test;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;

/**
 *
 */
public class NamedTableTypeReferenceTest {
    @Test
    public void testHashCodeAndEquals() {
        forClass(NamedTableTypeReference.class).verify();
    }
}
