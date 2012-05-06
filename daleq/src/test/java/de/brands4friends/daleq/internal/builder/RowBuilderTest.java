package de.brands4friends.daleq.internal.builder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.DaleqBuildException;
import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.TableDef;
import de.brands4friends.daleq.internal.container.PropertyContainer;
import de.brands4friends.daleq.internal.container.RowContainer;
import de.brands4friends.daleq.internal.structure.TableStructure;
import de.brands4friends.daleq.internal.structure.TableStructureFactory;

public class RowBuilderTest {

    private final static class PropertyContainerBean {
        PropertyDef propertyDef;
        String value;

        private PropertyContainerBean(final PropertyDef propertyDef, final String value) {
            this.propertyDef = propertyDef;
            this.value = value;
        }
    }

    private Context context;
    private TableStructure tableStructure;

    @TableDef("FOO")
    public static final class RowTable {
        public static final PropertyDef PROP_A = PropertyDef.pd(DataType.INTEGER);
        public static final PropertyDef PROP_B = PropertyDef.pd(DataType.INTEGER);
    }

    @Before
    public void setUp() throws Exception {
        context = new SimpleContext();
        tableStructure = new TableStructureFactory().create(RowTable.class);
    }

    @Test
    public void aRowWithJustProvidedProperties_should_beBuild(){
        assertThat(
                RowBuilder.row(23)
                        .p(RowTable.PROP_A,"FOO")
                        .p(RowTable.PROP_B,"BAR")
                        .build(context,tableStructure),
                is(row(
                        property(RowTable.PROP_A,"FOO"),
                        property(RowTable.PROP_B,"BAR")
                ))
        );
    }

    @Test @Ignore
    public void aRowWithJustDefaults_should_buildThatRow() {
        assertThat(
                RowBuilder.row(23).build(context, tableStructure),
                is(row(
                        property(RowTable.PROP_A, "23"),
                        property(RowTable.PROP_B, "23")
                ))
        );
    }

    @Test(expected = DaleqBuildException.class)
    public void propertyInRowContainsProperyDefNotInTableStructure_should_fail(){
        PropertyDef bar = PropertyDef.pd(DataType.VARCHAR);
        RowBuilder.row(42).p(bar,"foo").build(context, tableStructure);
    }

    private PropertyContainerBean property(PropertyDef propertyDef, String value) {
        return new PropertyContainerBean(propertyDef, value);
    }

    private RowContainer row(PropertyContainerBean... props) {
        return new RowContainer(tableStructure, Lists.transform(
                Arrays.asList(props),
                new Function<PropertyContainerBean, PropertyContainer>() {
                    @Override
                    public PropertyContainer apply(final PropertyContainerBean input) {
                        return new PropertyContainer(tableStructure.findStructureByDef(input.propertyDef), input.value);
                    }
                }));
    }
}
