package de.brands4friends.daleq.internal.types;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;

import org.junit.Test;

import com.google.common.base.Optional;

import nl.jqno.equalsverifier.Warning;

public class FieldTypeTest {

    @Test
    public void testHashCodeAndEquals() {
        forClass(FieldType.class)
                .withPrefabValues(Optional.class, Optional.of("a"), Optional.of("b"))
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
