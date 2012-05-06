package de.brands4friends.daleq.internal.builder;

import de.brands4friends.daleq.legacy.conversion.TypeConversion;

public class SimpleContext implements Context {
    private TypeConversion typeConversion = new TypeConversion();

    @Override
    public TypeConversion getTypeConversion() {
        return this.typeConversion;
    }
}
