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

public class TemplateValueDefaultProvider {

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

    private static ToTemplate NUMBER_TO_TEMPLATE = new ToTemplate() {
        @Override
        public TemplateValue map(final String fieldName, final String variable) {
            return new SubstitutingTemplateValue(variable);
        }
    };

    private final Map<DataType, ToTemplate> mapping;

    private TemplateValueDefaultProvider(final Map<DataType, ToTemplate> mapping) {
        this.mapping = mapping;
    }

    public TemplateValue toDefault(final DataType dataType, final String fieldName) {
        final ToTemplate toTemplate = mapping.get(dataType);
        if (toTemplate == null) {
            String msg = String.format(
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
        if (dataType.isNumber()) {
            return NUMBER_TO_TEMPLATE;
        }
        if (dataType.equals(DataType.DATE)) {
            // TODO
            return null;
        }
        if (dataType.equals(DataType.TIME) || dataType.equals(DataType.TIMESTAMP)) {
            // TODO
            return null;
        }
        return STRING_TO_TEMPLATE;
    }
}
