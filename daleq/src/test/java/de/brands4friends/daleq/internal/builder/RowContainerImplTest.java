package de.brands4friends.daleq.internal.builder;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;

import org.junit.Test;

import nl.jqno.equalsverifier.Warning;

public class RowContainerImplTest {

    @Test
    public void testHashcodeAndEquals() {
        forClass(RowContainerImpl.class).suppress(Warning.NULL_FIELDS).verify();
    }
}
