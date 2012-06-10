package de.brands4friends.daleq.internal.structure;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class CharTemplateValueTest {

    private CharTemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new CharTemplateValue();
    }

    @Test
    public void shouldBeA() {
        assertThat(templateValue.render(0), is("A"));
    }

    @Test
    public void shouldBeB() {
        assertThat(templateValue.render(1), is("B"));
    }

    @Test
    public void shouldBe_z() {
        assertThat(templateValue.render(57), is("z"));
    }

    @Test
    public void shouldBe_A_again() {
        assertThat(templateValue.render(58), is("A"));
    }
}
