package de.brands4friends.daleq.legacy.schema.def;

import static de.brands4friends.daleq.legacy.schema.Daleq.pt;
import static de.brands4friends.daleq.legacy.schema.Daleq.template;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import de.brands4friends.daleq.legacy.schema.PropertyType;
import de.brands4friends.daleq.legacy.schema.Template;
import de.brands4friends.daleq.legacy.schema.def.TemplateFactory;

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
