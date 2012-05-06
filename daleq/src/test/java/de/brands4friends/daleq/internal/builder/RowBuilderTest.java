package de.brands4friends.daleq.internal.builder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.dbunit.dataset.datatype.DataType;
import org.junit.Before;
import org.junit.Test;

import de.brands4friends.daleq.DaleqBuildException;
import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.internal.structure.TableStructure;
import de.brands4friends.daleq.internal.structure.TableStructureFactory;

public class RowBuilderTest {

    private Context context;
    private TableStructure tableStructure;
    private StructureBuilder sb;

    @Before
    public void setUp() throws Exception {
        context = new SimpleContext();
        tableStructure = new TableStructureFactory().create(ExampleTable.class);
        sb = new StructureBuilder(tableStructure);
    }

    @Test
    public void aRowWithJustProvidedProperties_should_beBuild(){
        assertThat(
                RowBuilder.row(23)
                        .p(ExampleTable.PROP_A, "FOO")
                        .p(ExampleTable.PROP_B, "BAR")
                        .build(context, tableStructure),
                is(sb.row(
                        sb.property(ExampleTable.PROP_A,"FOO"),
                        sb.property(ExampleTable.PROP_B,"BAR")
                ))
        );
    }

    @Test
    public void aRowWithJustDefaults_should_buildThatRow() {
        assertThat(
                RowBuilder.row(23).build(context, tableStructure),
                is(sb.row(
                        sb.property(ExampleTable.PROP_A, "23"),
                        sb.property(ExampleTable.PROP_B, "23")
                ))
        );
    }

    @Test(expected = DaleqBuildException.class)
    public void propertyInRowContainsProperyDefNotInTableStructure_should_fail(){
        PropertyDef bar = PropertyDef.pd(DataType.VARCHAR);
        RowBuilder.row(42).p(bar,"foo").build(context, tableStructure);
    }
}
