package de.brands4friends.daleq.jdbc.dbunit;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import de.brands4friends.daleq.internal.container.SchemaContainer;

public class FlatXmlIDataSetFactory implements IDataSetFactory{

    private static final String NULL_TOKEN = "[NULL]";

    /**
     * Converts a Schema into DbUnit's IDataSet.
     *
     * Nulls could be added eplixitly by filling "[NULL]" into the property values.
     *
     * @param schema the schema to be converted
     * @return a DbUnit dataset containing the data from the given Schema.
     *
     * @throws org.dbunit.dataset.DataSetException
     */
    public IDataSet create(final SchemaContainer schema) throws DataSetException {
        try {
            StringWriter stringWriter = new StringWriter();
            new FlatXmlConverter(NULL_TOKEN).writeTo(schema,stringWriter);
            String doc = stringWriter.toString();
            FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();

            FlatXmlDataSet dataset = flatXmlDataSetBuilder.build(new StringReader(doc));
            return decorateWithReplacement(dataset);

        } catch (IOException e) {
            // There ought to be no IO. The whole Writer handling is done in memory.
            throw new IllegalStateException(e);
        }

    }

    private IDataSet decorateWithReplacement(final IDataSet dataset) {
        ReplacementDataSet repDataset = new ReplacementDataSet(dataset);
        repDataset.addReplacementObject(NULL_TOKEN,null);
        return repDataset;
    }
}
