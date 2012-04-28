package de.brands4friends.daleq.schema;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Objects;

/**
 * A Schema consists of named DataLists.
 */
final class SimpleSchema implements Schema {

    private final List<Table> tables;

    SimpleSchema(List<Table> lists){
        this.tables = lists;
    }

    @Override
    public List<Table> getTables(){
        return Collections.unmodifiableList(tables);
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(tables);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof SimpleSchema) {
            final SimpleSchema that = (SimpleSchema) obj;

            return Objects.equal(tables, that.tables);
        }

        return false;
    }
}
