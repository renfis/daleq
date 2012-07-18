package de.brands4friends.daleq.core.internal.types;

import de.brands4friends.daleq.core.Daleq;
import de.brands4friends.daleq.core.DataType;
import de.brands4friends.daleq.core.FieldDef;
import de.brands4friends.daleq.core.TableDef;
import de.brands4friends.daleq.core.TableType;
import de.brands4friends.daleq.core.TableTypeReference;
import de.brands4friends.daleq.core.internal.builder.SimpleContext;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ClassBasedTableTypeReferenceTest {

    @TableDef("MY_TABLE")
    public static final class Table {
        public static final FieldDef ID = Daleq.fd(DataType.BIGINT);
    }

    @Test
    public void resolve_should_returnATable() {
        final TableTypeReference tableRef = ClassBasedTableTypeReference.of(Table.class);
        final TableType table = tableRef.resolve(new SimpleContext());

        assertThat(table.getName(), is("MY_TABLE"));
    }
}
