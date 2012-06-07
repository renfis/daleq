package de.brands4friends.daleq;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import de.brands4friends.daleq.internal.structure.TemplateValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class FieldDefTest{

    @Test
    public void testHashCodeAndEquals(){
        EqualsVerifier.forClass(FieldDef.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void aFieldWithName_should_haveName(){
        assertThat(FieldDef.fd(DataType.BIGINT).name("foo").hasName(), is(true));
    }

    @Test
    public void aFieldWithoutName_should_haveName(){
        assertThat(FieldDef.fd(DataType.BIGINT).hasName(), is(false));
    }

    @Test
    public void aFieldWithTemplate_should_haveATempalte(){
        assertThat(FieldDef.fd(DataType.INTEGER).template("foo").hasTemplate(), is(true));
    }

    @Test
    public void aFieldWithoutTemplate_should_haveNoTemplate(){
        assertThat(FieldDef.fd(DataType.INTEGER).hasTemplate(), is(false));
    }

    @Test
    public void aFieldTemplate_should_beCorrect(){
        assertThat(FieldDef.fd(DataType.INTEGER).template("foo").getTemplate(),is(new TemplateValue("foo")));
    }
}