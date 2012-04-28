package de.brands4friends.daleq.conversion;

/**
 * Internally Daleq uses DBUnit for inserting values
 * (that are Properties within Daleq) into database.
 *
 * Therefore Daleq writes instances of Property to xml because
 * DBUnit uses XML as data format for writing data into database.
 *
 * <b>Per default</b> Daleq writes the value of an Property to xml by simply calling
 * toString.
 *
 * For instances of type Date or the JodaTime DateTime
 * the toString representation is not appropriate for DBUnit
 * to write to the database.
 *
 * For such specific types (e.g. Date or DateTime) we use the mechanism
 * of a TypeConverter that is responsible for converting the value
 * to an string representation that can be handled by DBUnit.
 *
 * To enabled an new TypeConverter implementation you must
 * register the typeConverter into Daleq.
 */
public interface TypeConverter {
    /**
     * Converts the value to an string representation that can be handled by DBUnit.
     * The provided value has to be of the type that can be handled by the provider and must not be null.
     *
     * If value is if wrong type or value is null IllegalArgumentException gets thrown.
     *
     *
     * @param valueToConvert value to be converted to string representation.
     * @return the string representation of the given value has to be returned. may be null.
     * @throws IllegalArgumentException in case provided valueToConvert is null or is of wrong type thus cannot
     * be handled by the TypeConverter implementation.
     */
    String convert(Object valueToConvert);

    /**
     * What type of instances can be treated/converted by this implementation.
     * Example: for a java.util.Date converter you would return the class
     * java.util.Date here.
     *
     * @return the class that can be handled by the implementation. must never return null.
     */
    Class<?> getResponsibleFor();
}
