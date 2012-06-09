package de.brands4friends.daleq.internal.structure;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.brands4friends.daleq.test.EqualsAssert;

public class TemplateValueTest {

    @Test
    public void testHashCodeAndEquals(){
        EqualsAssert.assertProperEqualsAndHashcode(TemplateValue.class);
    }

    @Test
    public void renderATemplateWithAString_should_returnSubstitutedValue(){
        assertRendering("${_}", "FOO", "FOO");
    }

    @Test
    public void renderingATemplateWithoutVar_should_returnTemplateStr(){
        assertRendering("FOOBAR","BAZ","FOOBAR");
    }

    @Test
    public void anEscapedVar_should_returnTheVar(){
        assertRendering("$${_}","BAR","${_}");
    }

    @Test
    public void replacingAVarInAstring_should_returnTheReplacedStr(){
        assertRendering("ABC${_}EFGH","D","ABCDEFGH");
    }

    @Test
    public void null_shouldBe_null(){
        assertRendering(null,"FOO",null);
    }

    private void assertRendering(final String template, final String binding, final String expectedStr) {
        final TemplateValue templateValue = new TemplateValue(template);
        assertThat(templateValue.render(binding), is(expectedStr));
    }
}
