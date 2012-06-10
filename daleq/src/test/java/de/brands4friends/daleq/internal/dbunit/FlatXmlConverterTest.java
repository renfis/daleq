package de.brands4friends.daleq.internal.dbunit;

import static de.brands4friends.daleq.Daleq.aRow;
import static de.brands4friends.daleq.Daleq.aTable;
import static de.brands4friends.daleq.FieldDef.fd;
import static de.brands4friends.daleq.internal.dbunit.FlatXmlConverterTest.TheTable.A;
import static de.brands4friends.daleq.internal.dbunit.FlatXmlConverterTest.TheTable.B;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringWriter;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.Context;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.container.SchemaContainer;
import de.brands4friends.daleq.container.SchemaContainerImpl;
import de.brands4friends.daleq.container.TableContainer;
import de.brands4friends.daleq.internal.builder.SimpleContext;


public class FlatXmlConverterTest {

    @de.brands4friends.daleq.TableDef("table")
    public static final class TheTable {
        public static final FieldDef A = fd(DataType.VARCHAR).name("a");
        public static final FieldDef B = fd(DataType.VARCHAR).name("b");
    }

    private static final String NULL_TOKEN = "FOO_NULL";
    private FlatXmlConverter converter;
    private StringWriter writer;

    @Before
    public void setUp() {
        converter = new FlatXmlConverter(NULL_TOKEN);
        writer = new StringWriter();
    }

    @Test
    public void orderedProperties() throws IOException {
        assertWriteTo(
                aTable(TheTable.class).with(aRow(1).f(A, "1").f(B, "2")),
                "<table a=\"1\" b=\"2\"/>");
    }

    @Test
    public void writeNull() throws IOException {
        assertWriteTo(
                aTable(TheTable.class).with(aRow(1).f(A, null)),
                "<table a=\"" + NULL_TOKEN + "\" b=\"b-1\"/>");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwOnNullToken() throws IOException {
        writeToWriter(aTable(TheTable.class).with(aRow(1).f(A, NULL_TOKEN)));
    }

    private void assertWriteTo(final Table table, final String expectedTable) throws IOException {
        final String expected = asXml(expectedTable);
        writeToWriter(table);
        final String actual = writer.toString();
        assertThat(expected, is(actual));
    }

    private void writeToWriter(final Table table) throws IOException {
        final Context context = new SimpleContext();
        final TableContainer tableContainer = table.build(context);
        final SchemaContainer schemaContainer = new SchemaContainerImpl(Lists.newArrayList(tableContainer));
        converter.writeTo(schemaContainer, writer);
    }

    private String asXml(final String table) {
        return new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<dataset>")
                .append(table)
                .append("</dataset>").toString();
    }


}
