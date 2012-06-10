package de.brands4friends.daleq;

import org.dbunit.dataset.datatype.DataType;

import com.sun.istack.internal.NotNull;

public interface TemplateValueFactory {
    TemplateValue create(@NotNull DataType dataType, @NotNull String fieldName);
}
