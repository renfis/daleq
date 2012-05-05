package de.brands4friends.daleq.internal.container;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class RowContainerTest {

    @Test
    public void testHashcodeAndEquals(){
        EqualsAssert.assertProperEqualsAndHashcode(RowContainer.class);
    }
}
