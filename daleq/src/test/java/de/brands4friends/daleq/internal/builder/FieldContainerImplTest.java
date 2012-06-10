package de.brands4friends.daleq.internal.builder;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;

import org.junit.Test;

import com.google.common.base.Optional;

import nl.jqno.equalsverifier.Warning;

public class FieldContainerImplTest {

    @Test
    public void testHashcodeAndEquals() {
        forClass(FieldContainerImpl.class)
                .suppress(Warning.NULL_FIELDS)
                .withPrefabValues(Optional.class, Optional.of("A"), Optional.of("B"))
                .verify();
    }
}
