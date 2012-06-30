/*
 * Copyright 2012 brands4friends, Private Sale GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.brands4friends.daleq.internal.dbunit;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import de.brands4friends.daleq.SchemaData;

public class FlatXmlIDataSetFactory implements IDataSetFactory {

    private static final String NULL_TOKEN = "[___NULL___]";

    /**
     * Converts a Schema into DbUnit's IDataSet.
     * <p/>
     * Nulls could be added eplixitly by filling "[NULL]" into the property values.
     *
     * @param schema the schema to be converted
     * @return a DbUnit dataset containing the data from the given Schema.
     * @throws org.dbunit.dataset.DataSetException
     *
     */
    public IDataSet create(final SchemaData schema) throws DataSetException {
        try {
            final StringWriter stringWriter = new StringWriter();
            new FlatXmlConverter(NULL_TOKEN).writeTo(schema.getTables(), stringWriter);
            final String doc = stringWriter.toString();
            final FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();

            final FlatXmlDataSet dataset = flatXmlDataSetBuilder.build(new StringReader(doc));
            return decorateWithReplacement(dataset);

        } catch (IOException e) {
            // There ought to be no IO. The whole Writer handling is done in memory.
            throw new IllegalStateException(e);
        }

    }

    private IDataSet decorateWithReplacement(final IDataSet dataset) {
        final ReplacementDataSet repDataset = new ReplacementDataSet(dataset);
        repDataset.addReplacementObject(NULL_TOKEN, null);
        return repDataset;
    }
}
