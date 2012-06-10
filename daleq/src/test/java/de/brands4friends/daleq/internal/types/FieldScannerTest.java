package de.brands4friends.daleq.internal.types;

import static de.brands4friends.daleq.FieldDef.fd;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.TemplateValue;
import de.brands4friends.daleq.internal.template.StringTemplateValue;

public class FieldScannerTest {

    public static final String NAME = "some name";
    private FieldScanner scanner;

    @Before
    public void setUp() throws Exception {
        scanner = new FieldScanner();
    }

    static class WithoutPropertyDefs {

    }

    @Test(expected = IllegalArgumentException.class)
    public void scanningAClassWithoutAPropertyDef_should_fail() {
        scanner.scan(WithoutPropertyDefs.class);
    }

    static class WithNonStatic {
        public final FieldDef xID = FieldDef.fd(DataType.INTEGER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scanningWithNonStatic_should_fail() {
        scanner.scan(WithNonStatic.class);
    }

    static class WithNonFinal {
        public static FieldDef xID = FieldDef.fd(DataType.INTEGER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scanningWithNonFinal_should_fail() {
        scanner.scan(WithNonFinal.class);
    }

    static class WithPropertyDefs {
        public static final FieldDef ID = FieldDef.fd(DataType.INTEGER);
        public static final FieldDef NAME = FieldDef.fd(DataType.VARCHAR);
    }

    @Test
    public void scanningAClassWithPropertyDefs_should_extractThosePropertyDefs() {
        final Collection<FieldType> expected = Lists.newArrayList(
                new FieldType("ID", DataType.INTEGER, Optional.<TemplateValue>absent(), WithPropertyDefs.ID),
                new FieldType("NAME", DataType.VARCHAR, Optional.<TemplateValue>absent(), WithPropertyDefs.NAME)
        );
        assertThat(scanner.scan(WithPropertyDefs.class), is(expected));
    }

    static class WithExplicitName {
        public static final FieldDef ID = fd(DataType.INTEGER).name(NAME);
    }

    @Test
    public void scanningWithExplicitName_should_haveThatName() {
        final Collection<FieldType> expected = Lists.newArrayList(
                new FieldType(NAME, DataType.INTEGER, Optional.<TemplateValue>absent(), WithExplicitName.ID)
        );
        assertThat(scanner.scan(WithExplicitName.class), is(expected));
    }

    static class WithExplicitTemplate {
        public static final FieldDef NAME = fd(DataType.VARCHAR).template("some template");
    }

    @Test
    public void scanningWithExplicitTemplate_should_haveTheTemplate() {
        assertThat(
                scanner.scan(WithExplicitTemplate.class),
                contains(
                        new FieldType(
                                "NAME",
                                DataType.VARCHAR,
                                Optional.<TemplateValue>of(new StringTemplateValue("some template")),
                                WithExplicitTemplate.NAME)
                )
        );
    }
}
