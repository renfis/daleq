package de.brands4friends.daleq.internal.builder;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;

import org.junit.Test;

import com.google.common.base.Optional;

import nl.jqno.equalsverifier.Warning;

public class FieldHolderTest {

    @Test
    public void testHashCodeAndEquals() {
        forClass(FieldHolder.class)
                .withPrefabValues(Optional.class, Optional.of(new Object()), Optional.of(new Object()))
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();

    }
}
