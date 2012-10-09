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

package de.brands4friends.daleq.examples;

import static de.brands4friends.daleq.core.Daleq.aRow;
import static de.brands4friends.daleq.core.Daleq.aTable;
import static de.brands4friends.daleq.examples.ProductTable.ID;
import static de.brands4friends.daleq.examples.ProductTable.NAME;
import static de.brands4friends.daleq.examples.ProductTable.PRICE;
import static de.brands4friends.daleq.examples.ProductTable.SIZE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Longs;

import de.brands4friends.daleq.core.DaleqSupport;
import de.brands4friends.daleq.core.Table;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class JdbcProductDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    public static final Function<Product, Long> TO_ID = new Function<Product, Long>() {
        @Override
        public Long apply(@Nullable final Product input) {
            return input == null ? null : input.getId();
        }
    };

    @Autowired
    private DaleqSupport daleq;

    private JdbcProductDao productDao;

    @Override
    @Autowired
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
        this.productDao = new JdbcProductDao(dataSource);
    }

    @Test
    public void findById_should_returnExistingProduct() throws IOException {

        final long expectedId = 42L;

        final Table table = aTable(ProductTable.class).with(
                aRow(11).f(ID, expectedId)
        );

        daleq.printTable(table, System.out);
        daleq.insertIntoDatabase(table);

        final Product product = productDao.findById(expectedId);
        assertThat(product.getId(), is(expectedId));
    }

    @Test
    public void findBySize_should_returnThoseProductsHavingThatSize() {
        daleq.insertIntoDatabase(
                aTable(ProductTable.class)
                        .withRowsUntil(10)
                        .with(
                                aRow(10).f(SIZE, "S"),
                                aRow(11).f(SIZE, "S"),
                                aRow(12).f(SIZE, "M"),
                                aRow(13).f(SIZE, "L")
                        )
        );
        final List<Product> products = productDao.findBySize("S");

        assertProductsWithIds(products, 10L, 11L);
    }

    @Test
    public void save_should_insertTheProduct() {
        final BigDecimal price = aPrice(1200);
        final String name = "some name";
        final String size = "M";

        final Product product = aProduct(name, price, size);

        productDao.save(product);

        final Table expected = aTable(ProductTable.class).with(
                aRow(0).f(NAME, name).f(PRICE, "12.00").f(SIZE, size)
        );
        daleq.assertTableInDatabase(expected, ID);
    }

    @Test
    public void saveAll_should_inserAllThoseProducts() {
        final List<Product> products = Lists.newArrayList(
                aProduct("p1", aPrice(100), "S1"),
                aProduct("p2", aPrice(200), "S2"),
                aProduct("p3", aPrice(300), "S3"),
                aProduct("p4", aPrice(400), "S4")
        );

        productDao.saveAll(products);

        final Table expected = aTable(ProductTable.class).with(
                aRow(0).f(NAME, "p1").f(PRICE, "1.00").f(SIZE, "S1"),
                aRow(1).f(NAME, "p2").f(PRICE, "2.00").f(SIZE, "S2"),
                aRow(2).f(NAME, "p3").f(PRICE, "3.00").f(SIZE, "S3"),
                aRow(3).f(NAME, "p4").f(PRICE, "4.00").f(SIZE, "S4")
        );
        daleq.assertTableInDatabase(expected, ID);
    }

    private BigDecimal aPrice(final int priceVal) {
        return BigDecimal.valueOf(priceVal, 2);
    }

    private Product aProduct(final String name, final BigDecimal price, final String size) {
        final Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setSize(size);
        return product;
    }

    private void assertProductsWithIds(final List<Product> products, final long... expectedIds) {
        final Set<Long> ids = Sets.newHashSet(Iterables.transform(products, TO_ID));
        final Set<Long> expected = Sets.newHashSet(Longs.asList(expectedIds));
        assertThat(ids, is(expected));
    }
}
