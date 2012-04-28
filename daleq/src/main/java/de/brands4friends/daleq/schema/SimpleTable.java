package de.brands4friends.daleq.schema;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 *  A ordered collection of Rows.
 */
final class SimpleTable implements Table {


    private final String name;

    private final List<Row> rows;

    SimpleTable(String name, List<Row> rows){
        this.name = name;
        this.rows = Lists.newArrayList(rows);
    }

    SimpleTable(String name, Row... rows){
        this(name,Lists.newArrayList(rows));
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns an unmodifiable view of the rows.
     */
    @Override
    public List<Row> getRows(){
        return Collections.unmodifiableList(rows);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("rows",rows).toString();
    }

    /**
     * Adds copies of the given property to all rows in this Table.
     *
     * @param prop the Property, which will be added to all rows in the table.
     * @return Returns this to allow method chaining.
     */
    @Override
    public Table add(Property prop){
        for(Row r : rows){
            r.add(new SimpleProperty(prop));
        }
        return this;
    }

    /**
     * Adds the keys of the given otherList Table as supposed foreign keys to this Table.
     *
     * This will add a new property with the name asThisProperty to all rows in this Table,
     * which contains the value of the onOtherProperty.
     *
     * @param otherList the properties which will be added to this rows will be taken from otherList
     * @param onOtherProperty the property with the name of onOtherPropery will be taken
     * @param asThisProperty the property will be added with the name of as>ThisProperty
     *
     * @return this, to allow method chaining.
     *
     * @throws java.util.NoSuchElementException If otherList contains less elements than this Schema
     */
    @Override
    public Table join(Table otherList, PropertyType onOtherProperty, PropertyType asThisProperty){
        add(asThisProperty, Daleq.propertyOf(onOtherProperty, otherList));
        return this;
    }

    /**
     * Adds a new property with the given propertyName and a property value obtained from values to all rows
     * in this Table.
     *
     * The i-th row the Table gets the property value of the i-th String in the values Iterable.
     *
     * @param propertyType the type of the property, which will be added to all rows.
     * @param values the values added to the property on each each row will be taken from this collection
     *
     * @return this, to allow method chaining.
     * @throws java.util.NoSuchElementException if values contains less elements than the numbers of rows in this Table
     */
    @Override
    public <T> Table add(final PropertyType propertyType, final Iterable<T> values){
        Iterator<T> iter = values.iterator();
        for(Row r : rows){
            r.add(new SimpleProperty(propertyType,iter.next()));
        }
        return this;
    }

    /**
     * Returns an iterator over all rows in this Table
     */
    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    /**
     * Adds the given rows in all tables to this Table
     *
     * @param  tables the rows in these tables are added to the this table.
     * @return this, to allow method chaining
     */
    @Override
    public Table concat(Table... tables){
        for(Iterable<Row> rows : tables){
            doConcat(rows);
        }
        return this;
    }

    /**
     * Adds the given to this Table
     *
     * @param rows the rows, which will be added to the table.
     * @return this. to allow method chaining.
     */
    @Override
    public Table concat(Row... rows){
        doConcat(Arrays.asList(rows));
        return this;
    }

    /**
     * Creates a new table containing the same rows as this column, but without the given properties on each row.
     */
    @Override
    public Table without(String... propertyNames){
        List<Row> newRows = Lists.newArrayListWithCapacity(this.rows.size());
        for(Row row : this.rows){
            Row withoutProp = row.without(propertyNames);
            newRows.add(withoutProp);
        }
        return new SimpleTable(this.name,newRows);
    }

    /**
     * Creates a new instance of this table
     * @return
     */
    @Override
    public Table duplicate(){
        return new SimpleTable(name,Lists.newArrayList(rows));
    }

    private void doConcat(Iterable<Row> rows){
        for(Row row : rows){
            this.rows.add(row);
        }
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof SimpleTable) {
            final SimpleTable that = (SimpleTable) obj;

            return Objects.equal(name, that.name)
                    && Objects.equal(this.rows, that.rows);

        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(name, rows);
    }
}
