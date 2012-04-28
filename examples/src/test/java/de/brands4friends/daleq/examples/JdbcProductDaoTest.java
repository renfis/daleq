package de.brands4friends.daleq.examples;

import static de.brands4friends.daleq.common.Range.range;
import static de.brands4friends.daleq.examples.ProductTable.ID;
import static de.brands4friends.daleq.examples.ProductTable.TABLE_NAME;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.brands4friends.daleq.DaleqSupport;
import de.brands4friends.daleq.schema.Table;
import de.brands4friends.daleq.schema.Template;

@Ignore
public class JdbcProductDaoTest {

    private DaleqSupport daleq;
    private JdbcProductDao productDao;
    private Template template;

    @Before
    public void setUp(){
        DataSource dataSource = null;

        daleq = new DaleqSupport(dataSource);
        productDao = new JdbcProductDao(dataSource);

        template = ProductTable.template();
    }

    @Test
    public void findById_should_returnExistingProduct() {

        final long expectedId = 42l;

        final Table table =
                template.toTable(TABLE_NAME, range(0, 10))
                    .concat(template.toRow(11).with(ID.of(expectedId)));

        daleq.insertIntoDatabase(table);

        final Product product = productDao.findById(expectedId);
        assertThat(product.getId(), is(expectedId));
    }
}
