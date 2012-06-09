package de.brands4friends.daleq;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import de.brands4friends.daleq.internal.structure.SubstitutingTemplateValue;
import de.brands4friends.daleq.internal.structure.TemplateValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class FieldDefTest {

    public static final String NAME = "foo";
    public static final String TEMPLATE = "foo";

    @Test
    public void testHashCodeAndEquals() {
        EqualsVerifier.forClass(FieldDef.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void aFieldWithName_should_haveName() {
        assertThat(FieldDef.fd(DataType.BIGINT).name(NAME).hasName(), is(true));
    }

    @Test
    public void aFieldWithoutName_should_haveName() {
        assertThat(FieldDef.fd(DataType.BIGINT).hasName(), is(false));
    }

    @Test
    public void aFieldWithTemplate_should_haveATempalte() {
        assertThat(FieldDef.fd(DataType.INTEGER).template(TEMPLATE).hasTemplate(), is(true));
    }

    @Test
    public void aFieldWithoutTemplate_should_haveNoTemplate() {
        assertThat(FieldDef.fd(DataType.INTEGER).hasTemplate(), is(false));
    }

    @Test
    public void aFieldTemplate_should_beCorrect() {
        final TemplateValue expected = new SubstitutingTemplateValue(TEMPLATE);
        assertThat(FieldDef.fd(DataType.INTEGER).template(TEMPLATE).getTemplate(), is(expected));
    }
}
