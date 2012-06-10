package de.brands4friends.daleq.integration.tests.assertingtable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.brands4friends.daleq.Daleq;
import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.integration.IntegrationConfig;
import de.brands4friends.daleq.jdbc.DaleqSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationConfig.class)
public class AssertTableTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DaleqSupport daleq;

    @Test
    public void inserting_build_asserting() {
        final Table toBeInserted = Daleq.aTable(AssertTableTable.class).withRowsUntil(100);
        daleq.insertIntoDatabase(toBeInserted);

        final Table toBeCompared = Daleq.aTable(AssertTableTable.class).withRowsUntil(100);
        daleq.assertTableInDatabase(toBeCompared);
    }
}
