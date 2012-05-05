package de.brands4friends.daleq.internal.structure;

import static de.brands4friends.daleq.PropertyDef.pd;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.PropertyDef;

public class PropertyScannerTest {

    private PropertyScanner scanner;

    @Before
    public void setUp() throws Exception {
        scanner = new PropertyScanner();
    }

    static class WithoutPropertyDefs {

    }

    @Test(expected = IllegalArgumentException.class)
    public void scanningAClassWithoutAPropertyDef_should_fail(){
        scanner.scan(WithoutPropertyDefs.class);
    }

    static class WithNonStatic {
        public final PropertyDef ID   = pd(DataType.INTEGER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scanningWithNonStatic_should_fail(){
        scanner.scan(WithNonStatic.class);
    }

    static class WithNonFinal {
        public static PropertyDef ID   = pd(DataType.INTEGER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scanningWithNonFinal_should_fail(){
        scanner.scan(WithNonFinal.class);
    }

    static class WithPropertyDefs {
        public static final PropertyDef ID   = pd(DataType.INTEGER);
        public static final PropertyDef NAME = pd(DataType.VARCHAR);
    }

    @Test
    public void scanningAClassWithPropertyDefs_should_extractThosePropertyDefs(){
        final Collection<PropertyStructure> expected = Lists.newArrayList(
                new PropertyStructure("ID",DataType.INTEGER, TemplateValue.DEFAULT),
                new PropertyStructure("NAME",DataType.VARCHAR, TemplateValue.DEFAULT)
        );
        assertThat(scanner.scan(WithPropertyDefs.class), is(expected));
    }

    static class WithExplicitName {
        public static final PropertyDef ID = pd("foo",DataType.INTEGER);
    }

    @Test
    public void scanningWithExplicitName_should_haveThatName(){
        final Collection<PropertyStructure> expected = Lists.newArrayList(
                new PropertyStructure("foo",DataType.INTEGER, TemplateValue.DEFAULT)
        );
        assertThat(scanner.scan(WithExplicitName.class), is(expected));
    }
}
