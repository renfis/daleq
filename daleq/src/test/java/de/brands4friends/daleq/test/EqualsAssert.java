package de.brands4friends.daleq.test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public final class EqualsAssert {

    private EqualsAssert() {
    }

    public static <T> void assertProperEqualsAndHashcode(final Class<T> clazz) {
        final EqualsVerifier<T> verifier = EqualsVerifier.forClass(clazz).suppress(Warning.STRICT_INHERITANCE);
        verifier.verify();
    }

}
