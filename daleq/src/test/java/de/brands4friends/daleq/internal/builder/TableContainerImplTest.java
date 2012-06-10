package de.brands4friends.daleq.internal.builder;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;

import org.junit.Test;

import nl.jqno.equalsverifier.Warning;

public class TableContainerImplTest {

    @Test
    public void testHashcodeAndEquals() {
        forClass(TableContainerImpl.class).suppress(Warning.NULL_FIELDS).verify();
    }
}
