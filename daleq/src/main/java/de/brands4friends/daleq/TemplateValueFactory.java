package de.brands4friends.daleq;

import org.dbunit.dataset.datatype.DataType;

public interface TemplateValueFactory {
    TemplateValue create(DataType dataType, String fieldName);
}
