package de.brands4friends.daleq.internal.builder;

import de.brands4friends.daleq.Context;
import de.brands4friends.daleq.TypeConversion;
import de.brands4friends.daleq.internal.conversion.TypeConversionImpl;
import de.brands4friends.daleq.internal.template.TemplateValueFactory;
import de.brands4friends.daleq.internal.template.TemplateValueFactoryImpl;

public class SimpleContext implements Context {
    private final TypeConversion typeConversion = new TypeConversionImpl();
    private final TemplateValueFactory templateValueFactory = TemplateValueFactoryImpl.getInstance();

    @Override
    public TypeConversion getTypeConversion() {
        return this.typeConversion;
    }

    @Override
    public TemplateValueFactory getTemplateValueFactory() {
        return templateValueFactory;
    }
}
