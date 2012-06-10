package de.brands4friends.daleq;

import de.brands4friends.daleq.internal.conversion.TypeConversion;
import de.brands4friends.daleq.internal.template.TemplateValueFactory;

public interface Context {
    TypeConversion getTypeConversion();

    TemplateValueFactory getTemplateValueFactory();
}
