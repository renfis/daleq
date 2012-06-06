package de.brands4friends.daleq.internal.container;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;

import org.junit.Test;

import nl.jqno.equalsverifier.Warning;

public class RowContainerTest {

    @Test
    public void testHashcodeAndEquals(){
        forClass(RowContainer.class).suppress(Warning.NULL_FIELDS).verify();
    }
}
