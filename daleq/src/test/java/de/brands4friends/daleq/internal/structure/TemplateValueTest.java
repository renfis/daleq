package de.brands4friends.daleq.internal.structure;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class TemplateValueTest {

    @Test
    public void testHashCodeAndEquals(){
        EqualsAssert.assertProperEqualsAndHashcode(TemplateValue.class);
    }
}
