package de.brands4friends.daleq.legacy.schema;


import de.brands4friends.daleq.legacy.PropertyWriter;

/**
 * A Property is the atomic Unit of Daleq and models a column entry in a Database Table.
 */
final class SimpleProperty implements Property {

    private final PropertyType type;

    private final Object value;

    /**
     * Creates an instance of Property
     *
     * @param type  The Property's type. Null is not allowed.
     * @param value The Property's value. Null is not allowed. The value's toString() value is taken.
     *
     * @throws IllegalArgumentException if either name or value is null
     */
    SimpleProperty(PropertyType type, Object value) {
        if(type == null) {
            throw new IllegalArgumentException();
        }
        this.type = type;
        this.value = value;
    }

    /**
     * Copy Constructor
     * @param copyOf
     */
    SimpleProperty(Property copyOf){
        this.type = copyOf.getType();
        this.value = copyOf.getValue();
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public PropertyType getType() {
        return type;
    }

    @Override
    public void write(final PropertyWriter writer) {
        writer.writeMapped(this.type.getName(), this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleProperty property = (SimpleProperty) o;

        if (type != null ? !type.equals(property.type) : property.type != null) return false;
        if (value != null ? !value.equals(property.value) : property.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "p("+type+","+value+")";
    }
}
