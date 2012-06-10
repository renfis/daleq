package de.brands4friends.daleq.internal.types;

import java.util.List;

import de.brands4friends.daleq.TableDef;

public class TableTypeFactory {

    private final FieldScanner fieldScanner = new FieldScanner();

    public <T> TableType create(final Class<T> fromClass) {

        final TableDef tableDef = fromClass.getAnnotation(TableDef.class);
        if (tableDef == null) {
            throw new IllegalArgumentException("Expected @TableDef on class '" + fromClass.getCanonicalName() + "'");
        }

        final List<FieldType> fields = fieldScanner.scan(fromClass);
        return new TableType(tableDef.value(), fields);
    }
}
