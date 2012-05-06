package de.brands4friends.daleq.legacy.dbunit;

import de.brands4friends.daleq.internal.conversion.TypeConversion;
import de.brands4friends.daleq.legacy.PropertyWriter;

class ElementPropertyWriter implements PropertyWriter {

    private final TypeConversion typeConversion;

    private String result = null;

    public ElementPropertyWriter(TypeConversion typeConversion) {
        this.typeConversion = typeConversion;
    }

    public String getResult() {
        return result;
    }

    @Override
    public void writeMapped(final String name,final Object value) {
        this.result = typeConversion.convert(value);
    }

}
