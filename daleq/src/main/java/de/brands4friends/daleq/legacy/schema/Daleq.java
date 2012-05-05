package de.brands4friends.daleq.legacy.schema;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Provides a unique interface to create entities of the Daleq Data Generation Tool.
 *
 * The API Design encourages the static import of Daleq.* to allow an embedded DSL like usage. Usually
 * this would look like
 * <pre>

    Schema mySchema = schema(
                        table("MyTable",
                            row(p("column1","a"), p("column2","b")),
                                p("column1","c"), p("column2","d"))
                               )
                            );
 * </pre>
 */
public class Daleq {


    /**
     * Creates a Schema
     */
    public static Schema schema(Table... lists){
        return new SimpleSchema(Lists.<Table>newArrayList(lists));
    }

    /**
     * Creates a table
     */
    public static Table table(String name,Row ... rows) {
        return new SimpleTable(name,rows);
    }

    public static Row row(Property ... props) {
        return new SimpleRow(props);
    }

    public static Template template(Property ... props) {
        return new SimpleTemplate(props);
    }

    public static Template template(Iterable<Property> props) {
        return new SimpleTemplate(Lists.newArrayList(props));
    }

    /**
     * Creates a property.
     *
     */
    public static Property p(final String name, final DataType type, final Object value){

        if(name == null){
            throw new IllegalArgumentException();
        }

        return p(new PropertyType(name, type),value);
    }


    public static Property p(final PropertyType type, final Object value){
        if(type == null) {
            throw new IllegalArgumentException();
        }

        return new SimpleProperty(type,value);
    }

    public static PropertyType pt(final String name, final DataType dataType){
        return new PropertyType(name, dataType);
    }

    /**
     * Maps the iterable of rows to an iterable of Strings containing the values of the property with the given propertyName.
     *
     * @throws IllegalArgumentException If no such Property name exists in any of the rows.
     */
    // TODO wrong here! move to table, where it is used and where it makes more sense
    static Iterable<Object> propertyOf(final PropertyType propertyType, Iterable<Row> rows){
        return Iterables.transform(rows,new Function<Row,Object>(){

            public Object apply(Row row) {
                Property p = row.get(propertyType.getName());
                if(p == null)
                    throw new IllegalArgumentException("Property " + propertyType + " does not exist on row " + row);
                return p.getValue();
            }

        });
    }
}
