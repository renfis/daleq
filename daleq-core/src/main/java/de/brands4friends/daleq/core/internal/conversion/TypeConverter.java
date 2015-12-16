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

package de.brands4friends.daleq.core.internal.conversion;

/**
 * Internally Daleq uses DBUnit for inserting values
 * (that are Properties within Daleq) into database.
 * <p>
 * Therefore Daleq writes instances of Property to xml because
 * DBUnit uses XML as data format for writing data into database.
 * <p>
 * <b>Per default</b> Daleq writes the value of an Property to xml by simply calling
 * toString.
 * <p>
 * For instances of type Date or the JodaTime DateTime
 * the toString representation is not appropriate for DBUnit
 * to write to the database.
 * <p>
 * For such specific types (e.g. Date or DateTime) we use the mechanism
 * of a TypeConverter that is responsible for converting the value
 * to an string representation that can be handled by DBUnit.
 * <p>
 * To enabled an new TypeConverter implementation you must
 * register the typeConverter into Daleq.
 */
public interface TypeConverter {
    /**
     * Converts the value to an string representation that can be handled by DBUnit.
     * The provided value has to be of the type that can be handled by the provider and must not be null.
     * <p>
     * If value is if wrong type or value is null IllegalArgumentException gets thrown.
     *
     * @param valueToConvert value to be converted to string representation.
     * @return the string representation of the given value has to be returned. may be null.
     * @throws IllegalArgumentException in case provided valueToConvert is null or is of wrong type thus cannot
     *                                  be handled by the TypeConverter implementation.
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
