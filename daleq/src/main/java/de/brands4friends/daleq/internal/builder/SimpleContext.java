package de.brands4friends.daleq.internal.builder;

import de.brands4friends.daleq.internal.conversion.TypeConversion;

public class SimpleContext implements Context {
    private final TypeConversion typeConversion = new TypeConversion();

    @Override
    public TypeConversion getTypeConversion() {
        return this.typeConversion;
    }
}
