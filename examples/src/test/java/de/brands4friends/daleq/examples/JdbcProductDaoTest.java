package de.brands4friends.daleq.examples;

import static de.brands4friends.daleq.Daleq.aRow;
import static de.brands4friends.daleq.Daleq.aTable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.jdbc.DaleqSupport;
import de.brands4friends.daleq.jdbc.dbunit.DbUnitDaleqSupport;

@Ignore
public class JdbcProductDaoTest {

    private DaleqSupport daleq;
    private JdbcProductDao productDao;

    @Before
    public void setUp(){
        DataSource dataSource = null;

        daleq = new DbUnitDaleqSupport(dataSource);
        productDao = new JdbcProductDao(dataSource);
    }

    @Test
    public void findById_should_returnExistingProduct() {

        final long expectedId = 42l;

        final Table table = aTable(ProductTable.class).with(
                aRow(11).p(ProductTable.ID,expectedId)
        );

        daleq.insertIntoDatabase(table);

        final Product product = productDao.findById(expectedId);
        assertThat(product.getId(), is(expectedId));
    }
}
