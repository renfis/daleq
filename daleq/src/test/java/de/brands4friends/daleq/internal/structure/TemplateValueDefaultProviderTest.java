package de.brands4friends.daleq.internal.structure;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

public class TemplateValueDefaultProviderTest {

    private TemplateValueDefaultProvider defaultProvider;

    @Before
    public void setUp() throws Exception {
        defaultProvider = TemplateValueDefaultProvider.create();
    }

    @Test
    public void defaultTemplate_of_VARCHAR() {
        assertStringFieldRendering(DataType.VARCHAR);
    }

    @Test
    public void defaultTemplate_of_CHAR() {
        assertStringFieldRendering(DataType.CHAR);
    }

    @Test
    public void defaultTemplate_of_LONGVARCHAR() {
        assertStringFieldRendering(DataType.LONGVARCHAR);
    }

    @Test
    public void defaultTemplate_of_NCHAR() {
        assertStringFieldRendering(DataType.NCHAR);
    }

    @Test
    public void defaultTemplate_of_NVARCHAR() {
        assertStringFieldRendering(DataType.NVARCHAR);
    }

    @Test
    public void defaultTemplate_of_LONGNVARCHAR() {
        assertStringFieldRendering(DataType.LONGNVARCHAR);
    }

    @Test
    public void defaultTemplate_of_CLOB() {
        assertStringFieldRendering(DataType.CLOB);
    }

    @Test
    public void defaultTemplate_of_NUMERIC() {
        assertNumericFieldRendering(DataType.NUMERIC);
    }

    private void assertNumericFieldRendering(final DataType dataType) {
        assertRendering(dataType, "13");
    }

    @Test
    public void defaultTemplate_of_DECIMAL() {
        assertNumericFieldRendering(DataType.DECIMAL);
    }

    @Test
    public void defaultTemplate_of_BOOLEAN() {
        assertRendering(DataType.BOOLEAN, "1");
    }

    @Test
    public void defaultTemplate_of_BIT() {
        assertRendering(DataType.BIT, "1");
    }

    @Test
    public void defaultTemplate_of_INTEGER() {
        assertNumericFieldRendering(DataType.INTEGER);
    }

    @Test
    public void defaultTemplate_of_TINYINT() {
        assertNumericFieldRendering(DataType.TINYINT);
    }

    @Test
    public void defaultTemplate_of_SMALLINT() {
        assertNumericFieldRendering(DataType.SMALLINT);
    }

    @Test
    public void defaultTemplate_of_BIGINT() {
        assertNumericFieldRendering(DataType.BIGINT);
    }

    @Test
    public void defaultTemplate_of_REAL() {
        assertNumericFieldRendering(DataType.REAL);
    }

    @Test
    public void defaultTemplate_of_DOUBLE() {
        assertNumericFieldRendering(DataType.DOUBLE);
    }

    @Test
    public void defaultTemplate_of_FLOAT() {
        assertNumericFieldRendering(DataType.FLOAT);
    }

    @Test
    public void defaultTemplate_of_DATE() {
        assertRendering(DataType.DATE, "1970-01-14");
    }

    @Test
    public void defaultTemplate_of_TIME() {
        assertRendering(DataType.TIME, "1970-01-01 01:00:13.000");
    }

    @Test
    public void defaultTemplate_of_TIMESTAMP() {
        assertRendering(DataType.TIME, "1970-01-01 01:00:13.000");
    }

    @Test
    public void defaultTemplate_of_VARBINARY() {
        assertStringFieldRendering(DataType.VARBINARY);
    }

    @Test
    public void defaultTemplate_of_BINARY() {
        assertStringFieldRendering(DataType.BINARY);
    }

    @Test
    public void defaultTemplate_of_LONGVARBINARY() {
        assertStringFieldRendering(DataType.LONGVARBINARY);
    }

    @Test
    public void defaultTemplate_of_BLOB() {
        assertStringFieldRendering(DataType.BLOB);
    }

    @Test
    public void defaultTemplate_of_BIGINT_AUX_LONG() {
        assertNumericFieldRendering(DataType.BIGINT_AUX_LONG);
    }

    private void assertStringFieldRendering(final DataType dataType) {
        assertRendering(dataType, "theField-13");
    }

    private void assertRendering(final DataType dataType, final String expected) {
        assertThat(defaultProvider.toDefault(dataType, "theField").render(13L), is(expected));
    }

}