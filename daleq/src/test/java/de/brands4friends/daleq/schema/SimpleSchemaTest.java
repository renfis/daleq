package de.brands4friends.daleq.schema;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class SimpleSchemaTest {
    
    @Test
    public void testHashCodeAndEquals() throws Exception {
        EqualsVerifier.forClass(SimpleSchema.class).verify();
    }
}
