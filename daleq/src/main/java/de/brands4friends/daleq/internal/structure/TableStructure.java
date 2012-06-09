package de.brands4friends.daleq.internal.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.FieldDef;

public class TableStructure {

    private final String name;
    private final List<FieldStructure> fields;
    private final Map<FieldDef,FieldStructure> lookupByDef;

    public TableStructure(final String name, final List<FieldStructure> fields) {
        this.name = name;
        this.fields = fields;
        this.lookupByDef = Maps.uniqueIndex(fields,new Function<FieldStructure, FieldDef>() {
            @Override
            public FieldDef apply(final FieldStructure input) {
                return input.getOrigin();
            }
        });
    }

    public TableStructure(final String name, final FieldStructure... fields){
        this(name, Arrays.asList(fields));
    }

    public String getName() {
        return name;
    }

    public List<FieldStructure> getFields() {
        return fields;
    }

    public FieldStructure findStructureByDef(final FieldDef fieldDef){
        return lookupByDef.get(fieldDef);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof TableStructure) {
            final TableStructure that = (TableStructure) obj;

            return Objects.equal(name,that.name)
                    && Objects.equal(fields, that.fields);
        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(name, fields);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name",name)
                .add("properties", fields).toString();
    }
}
