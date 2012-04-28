package de.brands4friends.daleq.dbunit;

import de.brands4friends.daleq.PropertyWriter;
import de.brands4friends.daleq.conversion.TypeConversion;

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
    public void writePlain(final String name, final String value) {
        this.result = value;
    }

    @Override
    public void writeMapped(final String name,final Object value) {
        this.result = typeConversion.convert(value);
    }

}
