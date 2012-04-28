package de.brands4friends.daleq.schema.def;

import static com.google.common.collect.Iterables.transform;
import static de.brands4friends.daleq.schema.Daleq.template;

import java.util.Collection;
import java.util.Map;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import de.brands4friends.daleq.schema.Property;
import de.brands4friends.daleq.schema.PropertyType;
import de.brands4friends.daleq.schema.Template;

public class TemplateFactory {

    private static final Map<DataType,String> TEMPLATE_VALS = ImmutableMap.<DataType,String>builder()
            .put(DataType.INTEGER,"${_}")
            .build();

    public Template create(Class<?> definition){
        final Collection<PropertyType> propertyTypes = new PropertyTypeScanner().scan(definition);
        return template(transform(propertyTypes, new Function<PropertyType, Property>() {
            @Override
            public Property apply(final PropertyType propertyType) {
                final Object templateVal = TEMPLATE_VALS.get(propertyType.getDataType());
                return propertyType.of(templateVal);
            }
        }));
    }
}
