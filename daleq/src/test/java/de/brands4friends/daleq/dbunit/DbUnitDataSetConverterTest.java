package de.brands4friends.daleq.dbunit;

import static de.brands4friends.daleq.common.Range.range;
import static de.brands4friends.daleq.schema.Daleq.p;
import static de.brands4friends.daleq.schema.Daleq.schema;
import static de.brands4friends.daleq.schema.Daleq.template;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import de.brands4friends.daleq.schema.Schema;
import junit.framework.Assert;

/**
 *
 */
public class DbUnitDataSetConverterTest {

    @Test
    public void testConvert() throws DataSetException {

        Schema schema = schema(
                template(p("a", DataType.VARCHAR, "b"), p("c", DataType.VARCHAR, "d")).toTable("value", range(20))
        );

        DbUnitDataSetConverter converter = new DbUnitDataSetConverter();
        IDataSet dbUnitDataSet = converter.convertTo(schema);
        Assert.assertNotNull(dbUnitDataSet);
    }
}
