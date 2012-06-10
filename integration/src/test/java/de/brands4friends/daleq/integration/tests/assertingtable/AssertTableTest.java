package de.brands4friends.daleq.integration.tests.assertingtable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.brands4friends.daleq.integration.IntegrationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationConfig.class)
public class AssertTableTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Test
    public void test() {

    }
}
