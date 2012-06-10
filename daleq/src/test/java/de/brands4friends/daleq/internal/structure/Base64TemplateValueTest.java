package de.brands4friends.daleq.internal.structure;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class Base64TemplateValueTest {

    private Base64TemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new Base64TemplateValue();
    }

    @Test
    public void render() {
        assertThat(templateValue.render(2384768273462873264L), is("IRhmHBNgTLA="));
    }

    @Test
    public void render_zero() {
        assertThat(templateValue.render(0L), is("AAAAAAAAAAA="));
    }
}
