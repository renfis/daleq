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

abstract class AbstractTypeConverter<T> implements TypeConverter {

    private final Class<T> responsibleFor;

    public AbstractTypeConverter(final Class<T> responsibleFor) {
        this.responsibleFor = responsibleFor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final String convert(final Object valueToConvert) {

        if (valueToConvert == null) {
            throw new IllegalArgumentException();
        }

        if (!(responsibleFor.isAssignableFrom(valueToConvert.getClass()))) {
            final String targetType = valueToConvert.getClass().getCanonicalName();
            final String msg = "Tried to convert value [";
            throw new IllegalArgumentException(msg + valueToConvert + "] of type: [" + targetType + "]");
        }

        return doConvert((T) valueToConvert);
    }

    protected abstract String doConvert(final T valueToConvert);

    @Override
    public final Class<?> getResponsibleFor() {
        return responsibleFor;
    }
}
