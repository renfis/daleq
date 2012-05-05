package de.brands4friends.daleq.examples;

import static de.brands4friends.daleq.legacy.schema.Daleq.pt;

import org.dbunit.dataset.datatype.DataType;

import de.brands4friends.daleq.legacy.schema.Daleq;
import de.brands4friends.daleq.legacy.schema.PropertyType;
import de.brands4friends.daleq.legacy.schema.Template;

public class ProductTable {

    public static final String TABLE_NAME = "PRODUCT";

    public static final PropertyType ID    = pt("ID", DataType.INTEGER);
    public static final PropertyType NAME  = pt("NAME", DataType.VARCHAR);
    public static final PropertyType PRICE = pt("PRICE", DataType.DECIMAL);

    public static Template template() {
        return Daleq.template(ID.of("${_}"), NAME.of("product-name-${_}"));
    }
}
