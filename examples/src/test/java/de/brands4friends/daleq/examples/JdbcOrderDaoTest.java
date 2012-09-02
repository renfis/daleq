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
import static de.brands4friends.daleq.examples.OrderItemTable.ORDER_ID;
import static de.brands4friends.daleq.examples.OrderItemTable.PRODUCT_ID;
import static de.brands4friends.daleq.examples.OrderTable.CREATION;
import static de.brands4friends.daleq.examples.OrderTable.CUSTOMER_ID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

import de.brands4friends.daleq.core.DaleqSupport;
import de.brands4friends.daleq.core.Table;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class JdbcOrderDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    public static final int PRODUCT_1 = 1000;
    public static final int PRODUCT_10 = 1001;
    public static final int CUSTOMER_1 = 23001;
    public static final int CUSTOMER_2 = 23002;
    public static final int CUSTOMER_3 = 2303;
    
    @Autowired
    private DaleqSupport daleq;

    private JdbcOrderDao orderDao;

    @Override
    @Autowired
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
        this.orderDao = new JdbcOrderDao();
        this.orderDao.setDataSource(dataSource);
    }

    @Before
    public void setUp(){
        final Table customers = aTable(CustomerTable.class).with(
                aRow(CUSTOMER_1),
                aRow(CUSTOMER_2),
                aRow(CUSTOMER_3)
        );
        final Table products = aTable(ProductTable.class).with(
                aRow(PRODUCT_1).f(ProductTable.PRICE,"1.00"),
                aRow(PRODUCT_10).f(ProductTable.PRICE,"10.00")
        );
        daleq.insertIntoDatabase(customers,products);
    }

    @Test
    public void test() throws IOException {

        final long orderId = 11;
        final LocalDate creationDay = new LocalDate(2012,8,1);
        final DateTime creation = creationDay.toDateTime(new LocalTime(10, 30));

        final Table orders = aTable(OrderTable.class)
                .withRowsBetween(1, 10)
                .with(aRow(orderId))
                .allHaving(CUSTOMER_ID, CUSTOMER_1)
                .allHaving(CREATION, creation);

        final Table orderItems = aTable(OrderItemTable.class)
                .withRowsBetween(101,110)
                    .having(ORDER_ID, Lists.<Object>newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
                    .allHaving(PRODUCT_ID, PRODUCT_1)
                .with(
                        aRow(111).f(ORDER_ID,orderId).f(PRODUCT_ID,PRODUCT_10),
                        aRow(112).f(ORDER_ID,orderId).f(PRODUCT_ID,PRODUCT_10),
                        aRow(113).f(ORDER_ID,orderId).f(PRODUCT_ID,PRODUCT_10),
                        aRow(114).f(ORDER_ID,orderId).f(PRODUCT_ID,PRODUCT_10)
                );

        daleq.insertIntoDatabase(orders,orderItems);

        List<Order> actual = orderDao.findExpensiveOrders(CUSTOMER_1, creationDay, BigDecimal.valueOf(400,2));
        assertThat(actual.size(), is(1));
    }
}
