package de.brands4friends.daleq.examples;

import static de.brands4friends.daleq.Daleq.aRow;
import static de.brands4friends.daleq.Daleq.aTable;
import static de.brands4friends.daleq.examples.ProductTable.ID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.jdbc.DaleqSupport;
import de.brands4friends.daleq.jdbc.dbunit.DbUnitDaleqSupport;

@ContextConfiguration(classes = TestConfig.class)
public class JdbcProductDaoTest extends AbstractJUnit4SpringContextTests {

    private DaleqSupport daleq;
    private JdbcProductDao productDao;

    @Resource
    private DataSource dataSource;

    @Before
    public void setUp(){
        daleq = new DbUnitDaleqSupport(dataSource);
        productDao = new JdbcProductDao(dataSource);
    }

    @Test
    public void findById_should_returnExistingProduct() {

        final long expectedId = 42l;

        final Table table = aTable(ProductTable.class).with(
                aRow(11).p(ID,expectedId)
        );

        daleq.insertIntoDatabase(table);

        final Product product = productDao.findById(expectedId);
        assertThat(product.getId(), is(expectedId));
    }
}
