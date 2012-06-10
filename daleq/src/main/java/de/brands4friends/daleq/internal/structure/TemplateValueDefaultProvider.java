package de.brands4friends.daleq.internal.structure;

import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.BIGINT_AUX_LONG;
import static org.dbunit.dataset.datatype.DataType.BINARY;
import static org.dbunit.dataset.datatype.DataType.BIT;
import static org.dbunit.dataset.datatype.DataType.BLOB;
import static org.dbunit.dataset.datatype.DataType.BOOLEAN;
import static org.dbunit.dataset.datatype.DataType.CHAR;
import static org.dbunit.dataset.datatype.DataType.CLOB;
import static org.dbunit.dataset.datatype.DataType.DATE;
import static org.dbunit.dataset.datatype.DataType.DECIMAL;
import static org.dbunit.dataset.datatype.DataType.DOUBLE;
import static org.dbunit.dataset.datatype.DataType.FLOAT;
import static org.dbunit.dataset.datatype.DataType.INTEGER;
import static org.dbunit.dataset.datatype.DataType.LONGNVARCHAR;
import static org.dbunit.dataset.datatype.DataType.LONGVARBINARY;
import static org.dbunit.dataset.datatype.DataType.LONGVARCHAR;
import static org.dbunit.dataset.datatype.DataType.NCHAR;
import static org.dbunit.dataset.datatype.DataType.NUMERIC;
import static org.dbunit.dataset.datatype.DataType.NVARCHAR;
import static org.dbunit.dataset.datatype.DataType.REAL;
import static org.dbunit.dataset.datatype.DataType.SMALLINT;
import static org.dbunit.dataset.datatype.DataType.TIME;
import static org.dbunit.dataset.datatype.DataType.TIMESTAMP;
import static org.dbunit.dataset.datatype.DataType.TINYINT;
import static org.dbunit.dataset.datatype.DataType.VARBINARY;

import java.util.Map;

import org.dbunit.dataset.datatype.DataType;

import com.google.common.collect.Maps;

import de.brands4friends.daleq.DaleqBuildException;

public final class TemplateValueDefaultProvider {

    private static final DataType[] REGISTERED_DATA_TYPES = {
            DataType.VARCHAR, CHAR, LONGVARCHAR, NCHAR, NVARCHAR, LONGNVARCHAR, CLOB, NUMERIC, DECIMAL, BOOLEAN, BIT,
            INTEGER, TINYINT, SMALLINT, BIGINT, REAL, DOUBLE, FLOAT, DATE, TIME, TIMESTAMP,
            VARBINARY, BINARY, LONGVARBINARY, BLOB,
            //auxiliary types at the very end
            BIGINT_AUX_LONG
    };

    public static interface ToTemplate {
        TemplateValue map(String fieldName, String variable);
    }

    private static final ToTemplate STRING_TO_TEMPLATE = new ToTemplate() {
        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return new SubstitutingTemplateValue(fieldName + "-" + variable);
        }
    };

    private static final ToTemplate NUMBER_TO_TEMPLATE = new ToTemplate() {
        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return new SubstitutingTemplateValue(variable);
        }
    };

    private static final ModuloTemplateValue MOD2_TEMPLATE_VALUE = new ModuloTemplateValue(2);
    private static final ToTemplate MOD2_TO_TEMPLATE = new ToTemplate() {
        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return MOD2_TEMPLATE_VALUE;
        }
    };

    private static final DateTemplateValue DATE_TEMPLATE_VALUE = new DateTemplateValue();
    private static final ToTemplate DATE_TO_TEMPLATE = new ToTemplate() {
        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return DATE_TEMPLATE_VALUE;
        }
    };

    private static final TimestampTemplateValue TIMESTAMP_TEMPLATE_VALUE = new TimestampTemplateValue();
    private static final ToTemplate TIMESTAMP_TO_TEMPLATE = new ToTemplate() {
        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return TIMESTAMP_TEMPLATE_VALUE;
        }
    };

    private static final CharTemplateValue CHAR_TEMPLATE_VALUE = new CharTemplateValue();
    private static final ToTemplate CHAR_TO_TEMPLATE = new ToTemplate() {
        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return CHAR_TEMPLATE_VALUE;
        }
    };

    private static final Base64TemplateValue BASE64_TEMPLATE_VALUE = new Base64TemplateValue();
    private static final ToTemplate BASE64_TO_TEMPLATE = new ToTemplate() {
        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return BASE64_TEMPLATE_VALUE;
        }
    };

    private final Map<DataType, ToTemplate> mapping;

    private TemplateValueDefaultProvider(final Map<DataType, ToTemplate> mapping) {
        this.mapping = mapping;
    }

    public TemplateValue toDefault(final DataType dataType, final String fieldName) {
        final ToTemplate toTemplate = mapping.get(dataType);
        if (toTemplate == null) {
            final String msg = String.format(
                    "Cannot create a default TemplateValue for field %s, because the DataType %s is unknown.",
                    fieldName, dataType
            );
            throw new DaleqBuildException(msg);
        }
        return toTemplate.map(fieldName, SubstitutingTemplateValue.VAR);
    }

    public static TemplateValueDefaultProvider create() {
        final Map<DataType, ToTemplate> mapping = Maps.newHashMap();
        for (DataType dataType : REGISTERED_DATA_TYPES) {
            mapping.put(dataType, map(dataType));
        }
        return new TemplateValueDefaultProvider(mapping);
    }

    private static ToTemplate map(final DataType dataType) {
        if (dataType.equals(DataType.DATE)) {
            return DATE_TO_TEMPLATE;
        }
        if (dataType.equals(DataType.TIME) || dataType.equals(DataType.TIMESTAMP)) {
            return TIMESTAMP_TO_TEMPLATE;
        }
        if (dataType.equals(BOOLEAN) || dataType.equals(BIT)) {
            return MOD2_TO_TEMPLATE;
        }
        if (dataType.equals(CHAR)) {
            return CHAR_TO_TEMPLATE;
        }

        if (dataType.equals(VARBINARY) || dataType.equals(BINARY) || dataType.equals(LONGVARBINARY) || dataType.equals(BLOB)) {
            return BASE64_TO_TEMPLATE;
        }

        if (dataType.isNumber()) {
            return NUMBER_TO_TEMPLATE;
        }
        return STRING_TO_TEMPLATE;
    }
}
