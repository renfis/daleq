package de.brands4friends.daleq.legacy.dbunit;

import static de.brands4friends.daleq.legacy.schema.Daleq.p;
import static de.brands4friends.daleq.legacy.schema.Daleq.row;
import static de.brands4friends.daleq.legacy.schema.Daleq.schema;
import static de.brands4friends.daleq.legacy.schema.Daleq.table;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringWriter;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import de.brands4friends.daleq.legacy.schema.Schema;

/**
 *
 */
public class FlatXmlConverterTest {

    private static final String NULL_TOKEN = "FOO_NULL";
    private FlatXmlConverter converter;
    private StringWriter writer;

    @Before
    public void setUp(){
        converter = new FlatXmlConverter(NULL_TOKEN);
        writer = new StringWriter();
    }

    @Test
    public void testWriteTo() throws Exception {
        assertWriteTo(
            schema(
                table("table",
                        row(p("a", DataType.VARCHAR, "b"))
                )
        ), "<table a=\"b\"/>");
    }

    @Test
    public void orderedProperties() throws Exception {
        assertWriteTo(
                schema(
                        table("table",
                                row(p("b", DataType.VARCHAR, "2"),p("a", DataType.VARCHAR, "1"))
                        )
                ),
                "<table a=\"1\" b=\"2\"/>");
    }

    @Test
    public void writeNull() throws Exception {
        assertWriteTo(
                schema(table("table",row(p("a", DataType.VARCHAR, null)))),
                "<table a=\"" + NULL_TOKEN + "\"/>");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwOnNullToken() throws Exception {
        writeToWriter(schema(table("table",row(p("a", DataType.VARCHAR, NULL_TOKEN)))));
    }

    private void assertWriteTo(final Schema schema, final String expectedTable) throws IOException {
        String expected = asXml(expectedTable);
        writeToWriter(schema);
        String actual = writer.toString();
        assertThat(expected, is(actual));
    }

    private void writeToWriter(final Schema schema) throws IOException {
        converter.writeTo(schema,writer);
    }

    private String asXml(final String table) {
        return new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<dataset>")
                .append(table)
                .append("</dataset>").toString();
    }


}
