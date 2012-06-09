package de.brands4friends.daleq.internal.builder;

import de.brands4friends.daleq.internal.conversion.TypeConversion;
import de.brands4friends.daleq.internal.structure.TemplateValueDefaultProvider;

public class SimpleContext implements Context {
    private final TypeConversion typeConversion = new TypeConversion();
    private final TemplateValueDefaultProvider templateValueDefaultProvider = TemplateValueDefaultProvider.create();

    @Override
    public TypeConversion getTypeConversion() {
        return this.typeConversion;
    }

    @Override
    public TemplateValueDefaultProvider getTemplateValueDefaultProvider() {
        return templateValueDefaultProvider;
    }
}
