package de.brands4friends.daleq.core.internal.types;

import de.brands4friends.daleq.core.Daleq;
import de.brands4friends.daleq.core.DataType;
import de.brands4friends.daleq.core.FieldDef;
import de.brands4friends.daleq.core.TableDef;
import org.junit.Test;

import static nl.jqno.equalsverifier.EqualsVerifier.forClass;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ClassBasedTableTypeReferenceTest {

    @TableDef("MY_TABLE")
    public static final class Table {
        public static final FieldDef ID = Daleq.fd(DataType.BIGINT);
    }

    @Test
    public void testHashCodeAndEquals() {
        forClass(ClassBasedTableTypeReference.class).verify();
    }

    @Test
    public void twoReferencesToTheSameTable_should_beEqual() {
        assertThat(
                ClassBasedTableTypeReference.of(Table.class),
                is(ClassBasedTableTypeReference.of(Table.class))
        );
    }
}
