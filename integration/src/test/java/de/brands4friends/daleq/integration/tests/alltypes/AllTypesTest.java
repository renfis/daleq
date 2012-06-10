package de.brands4friends.daleq.integration.tests.alltypes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.brands4friends.daleq.Daleq;
import de.brands4friends.daleq.DaleqSupport;
import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.integration.IntegrationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationConfig.class)
public class AllTypesTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DaleqSupport daleq;

    @Test
    public void everyDataType_should_beInsertedIntoTheDatabase() {
        final Table table = Daleq.aTable(AllTypesTable.class).withRowsUntil(2000);
        daleq.insertIntoDatabase(table);
    }

}
