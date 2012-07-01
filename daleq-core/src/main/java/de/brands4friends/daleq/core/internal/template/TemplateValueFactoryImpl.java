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

package de.brands4friends.daleq.core.internal.template;

import static com.google.common.collect.ImmutableSet.of;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.brands4friends.daleq.core.DaleqBuildException;
import de.brands4friends.daleq.core.DataType;
import de.brands4friends.daleq.core.TemplateValue;

public final class TemplateValueFactoryImpl implements TemplateValueFactory {

    private static final Set<DataType> STRING_TYPES =
            of(DataType.VARCHAR, DataType.LONGVARCHAR, DataType.NVARCHAR, DataType.LONGNVARCHAR, DataType.CLOB);

    private static final Set<DataType> CHAR_TYPES = of(DataType.CHAR, DataType.NCHAR);

    private static final Set<DataType> BOOLEAN_TYPES = of(DataType.BOOLEAN, DataType.BIT);

    private static final Set<DataType> NUMBER_TYPES =
            of(DataType.NUMERIC, DataType.DECIMAL, DataType.INTEGER, DataType.TINYINT, DataType.SMALLINT,
                    DataType.BIGINT, DataType.REAL, DataType.DOUBLE, DataType.FLOAT, DataType.BIGINT_AUX_LONG);

    private static final Set<DataType> DATE_TYPES = of(DataType.DATE);

    private static final Set<DataType> TIMESTAMP_TYPES = of(DataType.TIME, DataType.TIMESTAMP);

    private static final Set<DataType> BLOB_TYPES = of(DataType.VARBINARY, DataType.BINARY,
            DataType.LONGVARBINARY, DataType.BLOB);

    public static interface ToTemplate {
        Set<DataType> mapsTypes();

        TemplateValue map(String fieldName, String variable);
    }

    private static final ToTemplate STRING_TO_TEMPLATE = new ToTemplate() {

        @Override
        public Set<DataType> mapsTypes() {
            return STRING_TYPES;
        }

        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return new StringTemplateValue(fieldName + "-" + variable);
        }
    };

    private static final ToTemplate NUMBER_TO_TEMPLATE = new ToTemplate() {
        @Override
        public Set<DataType> mapsTypes() {
            return NUMBER_TYPES;
        }

        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return new StringTemplateValue(variable);
        }
    };

    private static final ModuloTemplateValue MOD2_TEMPLATE_VALUE = new ModuloTemplateValue(2);
    private static final ToTemplate MOD2_TO_TEMPLATE = new ToTemplate() {
        @Override
        public Set<DataType> mapsTypes() {
            return BOOLEAN_TYPES;
        }

        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return MOD2_TEMPLATE_VALUE;
        }
    };

    private static final DateTemplateValue DATE_TEMPLATE_VALUE = new DateTemplateValue();
    private static final ToTemplate DATE_TO_TEMPLATE = new ToTemplate() {
        @Override
        public Set<DataType> mapsTypes() {
            return DATE_TYPES;
        }

        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return DATE_TEMPLATE_VALUE;
        }
    };

    private static final TimestampTemplateValue TIMESTAMP_TEMPLATE_VALUE = new TimestampTemplateValue();
    private static final ToTemplate TIMESTAMP_TO_TEMPLATE = new ToTemplate() {
        @Override
        public Set<DataType> mapsTypes() {
            return TIMESTAMP_TYPES;
        }

        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return TIMESTAMP_TEMPLATE_VALUE;
        }
    };

    private static final CharTemplateValue CHAR_TEMPLATE_VALUE = new CharTemplateValue();
    private static final ToTemplate CHAR_TO_TEMPLATE = new ToTemplate() {
        @Override
        public Set<DataType> mapsTypes() {
            return CHAR_TYPES;
        }

        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return CHAR_TEMPLATE_VALUE;
        }
    };

    private static final Base64TemplateValue BASE64_TEMPLATE_VALUE = new Base64TemplateValue();
    private static final ToTemplate BASE64_TO_TEMPLATE = new ToTemplate() {
        @Override
        public Set<DataType> mapsTypes() {
            return BLOB_TYPES;
        }

        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return BASE64_TEMPLATE_VALUE;
        }
    };

    private final Map<DataType, ToTemplate> mapping;

    private TemplateValueFactoryImpl(final Map<DataType, ToTemplate> mapping) {
        this.mapping = mapping;
    }

    @Override
    public TemplateValue create(final DataType dataType, final String fieldName) {
        final ToTemplate toTemplate = mapping.get(dataType);
        if (toTemplate == null) {
            final String msg = String.format(
                    "Cannot create a default TemplateValue for field %s, because the DataType %s is unknown.",
                    fieldName, dataType
            );
            throw new DaleqBuildException(msg);
        }
        return toTemplate.map(fieldName, StringTemplateValue.VAR);
    }

    public static TemplateValueFactory getInstance() {
        final Map<DataType, ToTemplate> mapping = Maps.newHashMap();

        final Collection<ToTemplate> toTemplates = Lists.newArrayList(
                NUMBER_TO_TEMPLATE, STRING_TO_TEMPLATE,
                DATE_TO_TEMPLATE, TIMESTAMP_TO_TEMPLATE, MOD2_TO_TEMPLATE, CHAR_TO_TEMPLATE, BASE64_TO_TEMPLATE
        );

        for (ToTemplate toTemplate : toTemplates) {
            for (DataType dataType : toTemplate.mapsTypes()) {
                mapping.put(dataType, toTemplate);
            }
        }
        return new TemplateValueFactoryImpl(mapping);
    }
}
