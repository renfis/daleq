package de.brands4friends.daleq.internal.structure;

import java.util.List;

import de.brands4friends.daleq.TableDef;

public class TableStructureFactory {

    private final FieldScanner fieldScanner = new FieldScanner();

    public <T> TableStructure create(Class<T> fromClass){

        final TableDef tableDef  =  fromClass.getAnnotation(TableDef.class);
        if(tableDef == null){
            throw new IllegalArgumentException("Expected @TableDef on class '" + fromClass.getCanonicalName() + "'");
        }

        final List<PropertyStructure> properties = fieldScanner.scan(fromClass);
        return new TableStructure(tableDef.value(),properties);
    }
}
