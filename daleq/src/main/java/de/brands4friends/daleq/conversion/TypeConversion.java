package de.brands4friends.daleq.conversion;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class TypeConversion {

private static final Map<Class<?>,TypeConverter> TYPE_CONVERTER_BY_CLASSNAME = buildMap();

    private static Map<Class<?>, TypeConverter> buildMap() {
        final DateTypeConverter dateTypeConverter = new DateTypeConverter();
        final DateTimeTypeConverter dateTimeTypeConverter = new DateTimeTypeConverter();

        return new ImmutableMap.Builder<Class<?>,TypeConverter>()
                .put(dateTypeConverter.getResponsibleFor(), dateTypeConverter)
                .put(dateTimeTypeConverter.getResponsibleFor(), dateTimeTypeConverter)
                .build();
    }

    public String convert(final Object value){
        if(value == null) {
            return null;
        }
        if (TYPE_CONVERTER_BY_CLASSNAME.containsKey(value.getClass())) {
            final TypeConverter typeConverter = TYPE_CONVERTER_BY_CLASSNAME.get(value.getClass());
            return typeConverter.convert(value);
        }
        return value.toString();
    }
}
