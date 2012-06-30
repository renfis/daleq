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
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.FieldData;
import de.brands4friends.daleq.RowData;
import de.brands4friends.daleq.SchemaData;
import de.brands4friends.daleq.TableData;

/**
 * Converts DataSets to XML File satisfying DbUnit's FlatXml Requirements.
 */
class FlatXmlConverter {

    private final String nullToken;

    private final DocumentFactory documentFactory = new DocumentFactory();

    public FlatXmlConverter(final String nullToken) {
        this.nullToken = nullToken;
    }

    /**
     * Converts the schema to an DbUnit conforming FlatXml file.
     *
     * @param schema the Schema which should be written to a FlatXml file.
     * @param writer the destination, where the XML file is written to
     * @throws java.io.IOException if the writer encounters IO problems.
     */
    public void writeTo(final SchemaData schema, final Writer writer) throws IOException {

        final Document doc = documentFactory.createDocument();
        final Element root = documentFactory.createElement("dataset");
        doc.setRootElement(root);

        for (TableData list : schema.getTables()) {
            addDataList(list, root);
        }

        final XMLWriter xmlWriter = new XMLWriter(writer);
        xmlWriter.write(doc);
    }

    private void addDataList(final TableData list, final Element root) {
        final String name = list.getName();
        for (RowData row : list.getRows()) {
            addRow(root, name, row);
        }
    }

    private void addRow(final Element root, final String name, final RowData row) {
        final Element elem = documentFactory.createElement(name);
        for (final FieldData prop : sortPropertiesByName(row.getFields())) {
            final String value = prepareValue(prop.getName(), prop.getValue());
            elem.add(documentFactory.createAttribute(elem, prop.getName(), value));
        }
        root.add(elem);
    }

    private Collection<FieldData> sortPropertiesByName(final Collection<FieldData> fields) {
        final List<FieldData> newProps = Lists.newArrayList(fields);
        Collections.sort(newProps, new Comparator<FieldData>() {
            public int compare(final FieldData o1, final FieldData o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return newProps;
    }

    private String prepareValue(final String name, final Optional<String> value) {
        if (!value.isPresent()) {
            return nullToken;
        }

        if (nullToken.equals(value.get())) {
            throw new IllegalArgumentException(
                    "The Property '" + name + "' contains the value '" + nullToken + "', " +
                            "although this is the implicitly inserted nullToken. " +
                            "Usually this happens, if you want to define a property with the value null. " +
                            "Do this, by simply creating a property, having a value with null like 'p(myName,null)'.");
        }

        return value.get();
    }

}
