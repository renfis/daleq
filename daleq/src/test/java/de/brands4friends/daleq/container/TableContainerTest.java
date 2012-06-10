package de.brands4friends.daleq.container;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;

import org.junit.Test;

import nl.jqno.equalsverifier.Warning;

public class TableContainerTest {

    @Test
    public void testHashcodeAndEquals() {
        forClass(TableContainerImpl.class).suppress(Warning.NULL_FIELDS).verify();
    }
}
