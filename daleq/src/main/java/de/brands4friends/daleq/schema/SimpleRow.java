package de.brands4friends.daleq.schema;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

/**
 * A Row is a container of Properties.
 */
final class SimpleRow implements Row {

    private final Map<String,Property> properties = Maps.newHashMap();

    SimpleRow(List<Property> props){
        for(Property p : props){
            properties.put(p.getName(),p);
        }
    }

    SimpleRow(Property... props){
        this(Arrays.asList(props));
    }

    /**
     * Copy Constructor
     *
     * @param copyOf duplicates this row
     */
    SimpleRow(Row copyOf){
        for(Property p : copyOf.getProperties()){
            Property copy = new SimpleProperty(p);
            properties.put(copy.getName(),copy);
        }
    }

    /**
     * Returns the property with the given name.
     *
     * If the Row does not contain such a property, null is returned.
     */
    @Override
    public Property get(String name){
        return properties.get(name);
    }

    /**
     * Adds or replaces an existing property with this property
     *
     * Actually this is an alias for #add
     *
     * @param prop
     * @return this, to allow method chaining.
     */
    @Override
    public Row with(Property prop){
        add(prop);
        return this;
    }


    /**
     * Returns an unmodifiable View of the stored Properties.
     */
    @Override
    public Collection<Property> getProperties(){
        return Collections.unmodifiableCollection(properties.values());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("properties",properties).toString();
    }

    /**
     * Creates a duplicate of this row.
     */
    @Override
    public SimpleRow duplicate(){
        return new SimpleRow(this);
    }

    /**
     * Adds the given Property to this Row.
     *
     * An existing property is replaced with the name one.
     *
     * @param property
     *
     * @return this instance, to allow method chaining.
     */
    @Override
    public Row add(Property property) {
        properties.put(property.getName(),property);
        return this;
    }

    /**
     * Creates a new row containing the all properties as the originating row without those given as arguments.
     */
    @Override
    public Row without(String... propertyNames){
        SimpleRow copy = duplicate();
        for(String propertyName : propertyNames){
            copy.properties.remove(propertyName);
        }
        return copy;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof SimpleRow) {
            final SimpleRow that = (SimpleRow) obj;

            return Objects.equal(properties, that.properties);
        }

        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(properties);
    }
}
