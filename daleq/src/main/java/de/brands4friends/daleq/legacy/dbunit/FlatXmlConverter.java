package de.brands4friends.daleq.legacy.dbunit;

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

import de.brands4friends.daleq.legacy.conversion.TypeConversion;
import de.brands4friends.daleq.legacy.schema.Property;
import de.brands4friends.daleq.legacy.schema.Row;
import de.brands4friends.daleq.legacy.schema.Schema;
import de.brands4friends.daleq.legacy.schema.Table;

/**
 * Converts DataSets to XML File satisfying DbUnit's FlatXml Requirements.
 */
class FlatXmlConverter {

    private final String nullToken;

    private final DocumentFactory documentFactory = new DocumentFactory();
    private final TypeConversion typeConversion = new TypeConversion();

    public FlatXmlConverter(final String nullToken) {
        this.nullToken = nullToken;
    }

    /**
     * Converts the schema to an DbUnit conforming FlatXml file.
     *
     * @param schema the Schema which should be written to a FlatXml file.
     * @param writer the destination, where the XML file is written to
     * @throws IOException if the writer encounters IO problems.
     */
    public void writeTo(Schema schema, Writer writer) throws IOException {

        Document doc = documentFactory.createDocument();
        Element root = documentFactory.createElement("dataset");
        doc.setRootElement(root);

        for(Table list : schema.getTables()){
            addDataList(list,root);
        }

        XMLWriter xmlWriter = new XMLWriter(writer);
        xmlWriter.write(doc);
    }

    private void addDataList(Table list,Element root){
        String name = list.getName();
        for(Row row : list.getRows()){
            addRow(root,name,row);
        }
    }

    private void addRow(Element root, String name, Row row) {
        final Element elem = documentFactory.createElement(name);
        for(final Property prop : sortPropertiesByName(row.getProperties())){
            final ElementPropertyWriter writer = new ElementPropertyWriter(typeConversion);
            prop.write(writer);
            final String value = prepareValue(prop.getName(),writer.getResult());
            elem.add(documentFactory.createAttribute(elem, prop.getName(), value));
        }
        root.add(elem);
    }

    private Collection<Property> sortPropertiesByName(Collection<Property> properties){
        List<Property> newProps = Lists.newArrayList(properties);
        Collections.sort(newProps, new Comparator<Property>(){
            public int compare(Property o1, Property o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return newProps;
    }

        private String prepareValue(final String name,final String value) {
        if(value == null){
            return nullToken;
        }

        if(value.equals(nullToken)){
            throw new IllegalArgumentException(
                    "The Property '" + name + "' contains the value '" + nullToken + "', " +
                            "although this is the implicitly inserted nullToken. " +
                            "Usually this happens, if you want to define a property with the value null. " +
                            "Do this, by simply creating a property, having a value with null like 'p(myName,null)'.");
        }

        return value;
    }

}
