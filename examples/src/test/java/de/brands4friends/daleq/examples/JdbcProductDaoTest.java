package de.brands4friends.daleq.examples;

import static de.brands4friends.daleq.Daleq.aRow;
import static de.brands4friends.daleq.Daleq.aTable;
import static de.brands4friends.daleq.examples.ProductTable.ID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.jdbc.DaleqSupport;
import de.brands4friends.daleq.jdbc.dbunit.DbUnitDaleqSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class JdbcProductDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    private DaleqSupport daleq;

    @Autowired
    private JdbcProductDao productDao;

    @Override @Autowired
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
        daleq = new DbUnitDaleqSupport(dataSource);
    }

    @Before
    public void setUp(){

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
