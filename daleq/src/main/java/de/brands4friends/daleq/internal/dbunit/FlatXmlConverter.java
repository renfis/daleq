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

import com.google.common.collect.Lists;

import de.brands4friends.daleq.FieldContainer;
import de.brands4friends.daleq.RowContainer;
import de.brands4friends.daleq.SchemaContainer;
import de.brands4friends.daleq.TableContainer;

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
    public void writeTo(final SchemaContainer schema, final Writer writer) throws IOException {

        final Document doc = documentFactory.createDocument();
        final Element root = documentFactory.createElement("dataset");
        doc.setRootElement(root);

        for (TableContainer list : schema.getTables()) {
            addDataList(list, root);
        }

        final XMLWriter xmlWriter = new XMLWriter(writer);
        xmlWriter.write(doc);
    }

    private void addDataList(final TableContainer list, final Element root) {
        final String name = list.getName();
        for (RowContainer row : list.getRows()) {
            addRow(root, name, row);
        }
    }

    private void addRow(final Element root, final String name, final RowContainer row) {
        final Element elem = documentFactory.createElement(name);
        for (final FieldContainer prop : sortPropertiesByName(row.getFields())) {
            final String value = prepareValue(prop.getName(), prop.getValue());
            elem.add(documentFactory.createAttribute(elem, prop.getName(), value));
        }
        root.add(elem);
    }

    private Collection<FieldContainer> sortPropertiesByName(final Collection<FieldContainer> fields) {
        final List<FieldContainer> newProps = Lists.newArrayList(fields);
        Collections.sort(newProps, new Comparator<FieldContainer>() {
            public int compare(final FieldContainer o1, final FieldContainer o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return newProps;
    }

    private String prepareValue(final String name, final String value) {
        if (value == null) {
            return nullToken;
        }

        if (value.equals(nullToken)) {
            throw new IllegalArgumentException(
                    "The Property '" + name + "' contains the value '" + nullToken + "', " +
                            "although this is the implicitly inserted nullToken. " +
                            "Usually this happens, if you want to define a property with the value null. " +
                            "Do this, by simply creating a property, having a value with null like 'p(myName,null)'.");
        }

        return value;
    }

}
