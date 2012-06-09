package de.brands4friends.daleq.internal.structure;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class DateTemplateValueTest {

    private DateTemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new DateTemplateValue();
    }

    @Test
    public void should_render_a_date(){
        assertThat(templateValue.render(0), is("1970-01-01"));
    }

    @Test
    public void render_should_increment_the_day(){
        assertThat(templateValue.render(10), is("1970-01-11"));
    }
}
