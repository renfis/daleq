package de.brands4friends.daleq.internal.template;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ModuloTemplateValueTest {

    private ModuloTemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new ModuloTemplateValue(2);
    }

    @Test
    public void testHashCodeAndEquals() {
        EqualsVerifier.forClass(ModuloTemplateValue.class).verify();
    }

    @Test
    public void value_should_beRendered() {
        assertThat(templateValue.render(0), is("0"));
    }

    @Test
    public void oddValue_should_be1() {
        assertThat(templateValue.render(23), is("1"));
    }

    @Test
    public void evenValue_should_be0() {
        assertThat(templateValue.render(42), is("0"));
    }
}
