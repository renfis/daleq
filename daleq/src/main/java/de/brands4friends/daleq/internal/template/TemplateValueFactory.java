package de.brands4friends.daleq.internal.template;

import org.dbunit.dataset.datatype.DataType;

public interface TemplateValueFactory {
    TemplateValue create(DataType dataType, String fieldName);
}
