package de.brands4friends.daleq.legacy.schema;

import org.junit.Test;

import de.brands4friends.daleq.legacy.schema.SimpleSchema;
import nl.jqno.equalsverifier.EqualsVerifier;

public class SimpleSchemaTest {
    
    @Test
    public void testHashCodeAndEquals() throws Exception {
        EqualsVerifier.forClass(SimpleSchema.class).verify();
    }
}
