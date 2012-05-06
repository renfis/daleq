package de.brands4friends.daleq.jdbc;

import static de.brands4friends.daleq.Daleq.aRow;
import static de.brands4friends.daleq.Daleq.aTable;
import static de.brands4friends.daleq.jdbc.FlatXmlConverterTest.TheTable.A;
import static de.brands4friends.daleq.jdbc.FlatXmlConverterTest.TheTable.B;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringWriter;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.internal.builder.Context;
import de.brands4friends.daleq.internal.builder.SimpleContext;
import de.brands4friends.daleq.internal.container.SchemaContainer;
import de.brands4friends.daleq.internal.container.TableContainer;


public class FlatXmlConverterTest {

    @de.brands4friends.daleq.TableDef("table")
    public static final class TheTable {
        public static final PropertyDef A = PropertyDef.pd("a",DataType.VARCHAR);
        public static final PropertyDef B = PropertyDef.pd("b",DataType.VARCHAR);
    }

    private static final String NULL_TOKEN = "FOO_NULL";
    private FlatXmlConverter converter;
    private StringWriter writer;

    @Before
    public void setUp(){
        converter = new FlatXmlConverter(NULL_TOKEN);
        writer = new StringWriter();
    }

    @Test
    public void orderedProperties() throws Exception {
        assertWriteTo(
                aTable(TheTable.class).with(aRow(1).p(A, "1").p(B, "2")),
                "<table a=\"1\" b=\"2\"/>");
    }

    @Test
    public void writeNull() throws Exception {
        assertWriteTo(
                aTable(TheTable.class).with(aRow(1).p(A, null)),
                "<table a=\"" + NULL_TOKEN + "\" b=\"1\"/>");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwOnNullToken() throws Exception {
        writeToWriter(aTable(TheTable.class).with(aRow(1).p(A, NULL_TOKEN)));
    }

    private void assertWriteTo(final Table table,final String expectedTable) throws IOException {
        String expected = asXml(expectedTable);
        writeToWriter(table);
        String actual = writer.toString();
        assertThat(expected, is(actual));
    }

    private void writeToWriter(final Table table) throws IOException {
        final Context context = new SimpleContext();
        final TableContainer tableContainer = table.build(context);
        final SchemaContainer schemaContainer = new SchemaContainer(Lists.newArrayList(tableContainer));
        converter.writeTo(schemaContainer,writer);
    }

    private String asXml(final String table) {
        return new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<dataset>")
                .append(table)
                .append("</dataset>").toString();
    }


}
