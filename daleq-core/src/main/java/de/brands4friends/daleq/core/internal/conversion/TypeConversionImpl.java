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

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TypeConversionImpl implements TypeConversion {

    private static final Map<Class<?>, TypeConverter> TYPE_CONVERTER_BY_CLASSNAME =
            Maps.uniqueIndex(Lists.newArrayList(
                    new DateTypeConverter(),
                    new DateTimeTypeConverter(),
                    new LocalDateConverter()),

                    new Function<TypeConverter, Class<?>>() {
                        @Override
                        public Class<?> apply(@Nullable final TypeConverter input) {
                            return input == null ? null : input.getResponsibleFor();
                        }
                    });

    @Override
    public String convert(@Nullable final Object value) {
        if (value == null) {
            return null;
        }
        if (TYPE_CONVERTER_BY_CLASSNAME.containsKey(value.getClass())) {
            final TypeConverter typeConverter = TYPE_CONVERTER_BY_CLASSNAME.get(value.getClass());
            return typeConverter.convert(value);
        }
        return value.toString();
    }
}
