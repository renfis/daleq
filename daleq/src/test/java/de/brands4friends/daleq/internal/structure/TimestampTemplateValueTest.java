package de.brands4friends.daleq.internal.structure;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class TimestampTemplateValueTest {

    private TimestampTemplateValue templateValue;

    @Before
    public void setUp() throws Exception {
        templateValue = new TimestampTemplateValue();
    }

    @Test
    public void should_render_a_datetime(){
        assertThat(templateValue.render(0), is("1970-01-01 01:00:00.000"));
    }

    @Test
    public void should_render_increment_time(){
        assertThat(templateValue.render(100), is("1970-01-01 01:01:40.000"));
    }
}
