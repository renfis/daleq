package de.brands4friends.daleq.internal.builder;

import de.brands4friends.daleq.internal.conversion.TypeConversion;
import de.brands4friends.daleq.internal.template.TemplateValueDefaultProvider;

public interface Context {
    TypeConversion getTypeConversion();

    TemplateValueDefaultProvider getTemplateValueDefaultProvider();
}
