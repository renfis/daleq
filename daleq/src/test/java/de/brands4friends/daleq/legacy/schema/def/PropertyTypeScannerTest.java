package de.brands4friends.daleq.legacy.schema.def;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

import de.brands4friends.daleq.legacy.schema.Daleq;
import de.brands4friends.daleq.legacy.schema.PropertyType;

public class PropertyTypeScannerTest {

    private static final class WithoutPropertyTypes {

    }

    private PropertyTypeScanner scanner;

    @Before
    public void setUp() throws Exception {
        scanner = new PropertyTypeScanner();
    }

    @Test
    public void scan_should_returnTheDeclaredPropertyFields(){
        final Collection<PropertyType> result = scanner.scan(MyDefinition.class);
        final Collection<PropertyType> expected = Sets.newHashSet(
                Daleq.pt("ID", DataType.INTEGER));
        assertThat(result, is(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void scanningAClassWithoutPropertyTypes_should_fail(){
        scanner.scan(WithoutPropertyTypes.class);
    }
}
