package de.brands4friends.daleq.schema.def;

import static de.brands4friends.daleq.schema.Daleq.pt;
import static de.brands4friends.daleq.schema.Daleq.template;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import de.brands4friends.daleq.schema.PropertyType;
import de.brands4friends.daleq.schema.Template;

public class TemplateFactoryTest {

    static class SomeDefinition {
        public static final PropertyType ID   = pt("ID", DataType.INTEGER);
        public static final PropertyType NAME = pt("NAME",DataType.INTEGER);
    }

    @Test
    public void createADefinition_should_returnTemplateWithAllProperties(){
        TemplateFactory templateFactory = new TemplateFactory();
        Template template =  templateFactory.create(SomeDefinition.class);

        assertThat(
                template,
                is(template(
                        SomeDefinition.ID.of("${_}"),
                        SomeDefinition.NAME.of("${_}")
                ))
        );
    }
}
