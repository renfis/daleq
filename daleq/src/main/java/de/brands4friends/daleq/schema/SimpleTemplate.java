package de.brands4friends.daleq.schema;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

final class SimpleTemplate implements Template {

    private static final String DEFAULT_VAR_NAME = "_";

    private final Map<String,Property> properties = Maps.newHashMap();

    SimpleTemplate(List<Property> props){
        for(Property p : props){
            properties.put(p.getName(),p);
        }
    }

    SimpleTemplate(Property... props){
        this(Arrays.asList(props));
    }

    /**
     * Expands a single ROw to a full Table of Rows, driven by values obtained from generateByIterable.
     *
     * This Row is used as a template to populate the Table. For each Element an in generateByIterable a new Row
     * is created as a duplicate of this row. On this new row each property value is scanned for a substitution variable
     * <code>${varName}</code>. This variable is substituted by value obtained from generateByIterable.
     *
     * @param tableName the name of the newly created Table
     * @param generateByIterable for each Element in this Iterable a new row is created. The value of the Iterable is subject
     *        to replace variables in the template row's property values.
     * @param varName the name of the substitution variable in the property values.
     *
     * @return a Table consisting of new Rows created by duplicating this row.
     *
     * @see {@link #substitute(String, Object)} for details on the variable substitution
     */
    @Override
    public <T> Table toTable(String tableName, Iterable<T> generateByIterable, String varName){

        List<Row> resultRows = Lists.newArrayList();

        for(T iter : generateByIterable){
            resultRows.add(substitute(varName, iter.toString()));
        }
        return new SimpleTable(tableName, resultRows);
    }

    /**
     * Expands a single Row to a full Table of Rows.
     *
     * Calls {@link #toTable(String,Iterable, String)} with the default variable <code>${_}</code>.
     *
     * @param tableName the name of the newly created Table
     * @param expandOnIterable for each Element in this Iterable a new row is created. The value of the Iterable is subject
     *        to replace variables in the template row's property values.
     *
     * @return a Table consisting of new Rows created by duplicating this row.
     */
    @Override
    public <T> Table toTable(String tableName, Iterable<T> expandOnIterable){
        return toTable(tableName,expandOnIterable, DEFAULT_VAR_NAME);
    }

    /**
     * Expects this Row to be a template and creates an instance of this row with the variable varName
     * replaces by varValue
     * @param varValue the value that should be replaced in all occurances of the variable
     * @param varName the variable name

     * @return a new instance of Row, with all occurances of ${varName} substituted by varValue
     */
    @Override
    public Row toRow(Object varValue, String varName){
        return substitute(varName, varValue);
    }

    /**
     * Like #toRow(String,String), but with the default varName
     *
     * @param varValue
     * @return
     */
    @Override
    public Row toRow(Object varValue){
        return toRow(varValue,DEFAULT_VAR_NAME);
    }

        /**
     * Substitutes occurrences of variables with name varName in all property values of this row.
     *
     * The substitution is done by Apache Commons Lang StrSubstitutor. See that documentation on details of variable
     * substitution.
     *
     * @param varName
     * @param varValue
     */
    private Row substitute(String varName, Object varValue){
        final Map<String,String> parameterMapping =
                new ImmutableMap.Builder<String,String>()
                        .put(varName, varValue.toString())
                        .build();

        final List<Property> newProperties = Lists.newArrayList();
        for(Property prop : this.properties.values()) {
            newProperties.add(substituteProperty(parameterMapping, prop));
        }
        return new SimpleRow(newProperties);
    }

    private Property substituteProperty(final Map<String, String> parameterMapping, final Property prop) {
        final Object value = prop.getValue();
        if (!(value instanceof String)) {
            return prop;
        }

        final String oldValue = (String) value;
        final String replacedValue = StrSubstitutor.replace(oldValue, parameterMapping);

        if (oldValue.equals(replacedValue)) {
            return prop;
        }

        return new SimpleProperty(prop.getType(), replacedValue);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof SimpleTemplate) {
            final SimpleTemplate that = (SimpleTemplate) obj;

            return Objects.equal(properties, that.properties);
        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(properties);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("properties",properties).toString();
    }
}
