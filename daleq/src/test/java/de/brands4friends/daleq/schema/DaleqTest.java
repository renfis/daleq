package de.brands4friends.daleq.schema;

import static de.brands4friends.daleq.common.Range.range;
import static de.brands4friends.daleq.schema.Daleq.p;
import static de.brands4friends.daleq.schema.Daleq.pt;
import static de.brands4friends.daleq.schema.Daleq.row;
import static de.brands4friends.daleq.schema.Daleq.table;
import static de.brands4friends.daleq.schema.Daleq.template;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 *
 */
public class DaleqTest {

    @Test
    public void testPropertyCreation(){
        assertEquals(new SimpleProperty(new PropertyType("foo", DataType.VARCHAR),"bar"),p(pt("foo",DataType.VARCHAR),"bar"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPropertyCreationWithNullName(){
        p((PropertyType) null,"bar");
    }

    @Test
    public void testPropertyCreationWithNullValue(){
        assertEquals(new SimpleProperty(new PropertyType("foo",DataType.VARCHAR),null), p(pt("foo",DataType.VARCHAR),null));
    }

    @Test
    public void testRowCreation(){
        Row r = row(p("a", DataType.VARCHAR, "b"),p("c", DataType.VARCHAR, "d"));
        assertNotNull(r);
        assertEquals(Sets.newHashSet(p("a", DataType.VARCHAR, "b"),p("c", DataType.VARCHAR, "d")), Sets.newHashSet(r.getProperties()));
    }

    @Test
    public void testScenarioWith1to1Association(){

        // setup 5 accounts
        Table accounts= template(ID.of("${_}"), NAME.of("Account ${_}")).toTable("accounts", range(5));
        // setup 5 addresses
        Table addresses = template(ID.of("${id}"),STREET.of("A Street")).toTable("addresses", range(5, 10), "id");
        // assign account ids as foreign keys to the addresses
        addresses.join(accounts,ID,ACCOUNT_ID);

        Table expectedAccounts = table("accounts",
                row(ID.of("0"),NAME.of("Account 0")),
                row(ID.of("1"),NAME.of("Account 1")),
                row(ID.of("2"),NAME.of("Account 2")),
                row(ID.of("3"),NAME.of("Account 3")),
                row(ID.of("4"),NAME.of("Account 4"))
        );

        Table expectedAddresses = table("addresses",
                row(ID.of("5"),STREET.of("A Street"),ACCOUNT_ID.of("0")),
                row(ID.of("6"),STREET.of("A Street"),ACCOUNT_ID.of("1")),
                row(ID.of("7"),STREET.of("A Street"),ACCOUNT_ID.of("2")),
                row(ID.of("8"),STREET.of("A Street"),ACCOUNT_ID.of("3")),
                row(ID.of("9"),STREET.of("A Street"),ACCOUNT_ID.of("4"))
        );

        assertThat(accounts,is(expectedAccounts));
        assertThat(addresses, is(expectedAddresses));

    }

    private static final PropertyType ID            = pt("id",DataType.INTEGER);
    private static final PropertyType NAME          = pt("name",DataType.VARCHAR);
    private static final PropertyType STREET        = pt("street",DataType.VARCHAR);
    private static final PropertyType ACCOUNT_ID    = pt("account_id", DataType.INTEGER);

}
