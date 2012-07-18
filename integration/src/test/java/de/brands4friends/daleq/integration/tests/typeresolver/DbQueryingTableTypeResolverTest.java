package de.brands4friends.daleq.integration.tests.typeresolver;

import de.brands4friends.daleq.core.DataType;
import de.brands4friends.daleq.core.FieldType;
import de.brands4friends.daleq.core.TableType;
import de.brands4friends.daleq.core.internal.types.DbQueryingTableTypeResolver;
import de.brands4friends.daleq.core.internal.types.NamedTableTypeReference;
import de.brands4friends.daleq.integration.IntegrationConfig;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationConfig.class)
public class DbQueryingTableTypeResolverTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DataSource dataSource;
    private DbQueryingTableTypeResolver resolver;


    @Before
    public void setUp() throws Exception {
        resolver = new DbQueryingTableTypeResolver(dataSource);
    }

    @Test
    public void should_resolveASSERT_TABLE() {
        final TableType table = resolver.resolve(new NamedTableTypeReference("ASSERT_TABLE"));
        assertThat(table.getFields(), containsInAnyOrder(
                field("ID", DataType.INTEGER),
                field("NAME", DataType.VARCHAR),
                field("AMOUNT", DataType.DECIMAL)));
    }

// TODO needs rework, it seems that mapping all sql types ain't work as expected.    
//    @Test
//    public void should_resolveAllTable() {
//        final TableType table = resolver.resolve(new NamedTableTypeReference("ALL_TYPES"));
//
//        assertThat(table, is(notNullValue()));
//        assertThat(table.getFields().size(),is(22));
//        assertThat(table.getFields().subList(0,3), Matchers.<FieldType>containsInAnyOrder(
//                field("A_VARCHAR", DataType.VARCHAR),
//                field("A_CHAR", DataType.CHAR),
//                field("A_LONGVARCHAR", DataType.LONGVARCHAR)
////                field("A_NVARCHAR", DataType.NVARCHAR)
//
////                field("A_CLOB", DataType.CLOB),
////                field("A_NUMERIC", DataType.NUMERIC),
////                field("A_DECIMAL", DataType.DECIMAL),
////                field("A_BOOLEAN", DataType.BOOLEAN),
////                field("A_BIT", DataType.BIT),
////                field("A_INTEGER", DataType.INTEGER),
////                field("A_TINYINT", DataType.TINYINT),
////                field("A_SMALLINT", DataType.SMALLINT),
////                field("A_BIGINT", DataType.BIGINT),
////                field("A_REAL", DataType.REAL),
////                field("A_DOUBLE", DataType.DOUBLE),
////                field("A_FLOAT", DataType.FLOAT),
////                field("A_DATE", DataType.DATE),
////                field("A_TIMESTAMP", DataType.TIMESTAMP),
////                field("A_VARBINARY", DataType.VARBINARY),
////                field("A_BINARY", DataType.BINARY),
////                field("A_LONGVARBINARY", DataType.LONGVARBINARY),
////                field("A_BLOB", DataType.BLOB)
//        ));
//    }

    private Matcher<FieldType> field(final String name, final DataType dataType) {
        return new BaseMatcher<FieldType>() {

            @Override
            public boolean matches(Object item) {
                if (!(item instanceof FieldType)) {
                    return false;
                }
                final FieldType fieldType = (FieldType) item;
                return fieldType.getName().equals(name)
                        && fieldType.getDataType().equals(dataType);

            }

            @Override
            public void describeTo(Description description) {
                description
                        .appendText("FieldType with name = ").appendValue(name)
                        .appendText(", dataType = ").appendValue(dataType);

            }
        };
    }
}
