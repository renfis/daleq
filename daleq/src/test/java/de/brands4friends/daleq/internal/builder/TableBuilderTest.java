package de.brands4friends.daleq.internal.builder;

import static de.brands4friends.daleq.Daleq.aRow;
import static de.brands4friends.daleq.Daleq.aTable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.brands4friends.daleq.internal.container.TableContainer;
import de.brands4friends.daleq.internal.structure.TableStructure;
import de.brands4friends.daleq.internal.structure.TableStructureFactory;

public class TableBuilderTest {

    private Context context;
    private TableStructure tableStructure;

    @Before
    public void setUp() throws Exception {
        context = new SimpleContext();
        tableStructure = new TableStructureFactory().create(ExampleTable.class);
    }

    @Test
    public void aTableWithARow_should_beBuilt(){
        final StructureBuilder sb = new StructureBuilder(tableStructure);
        final TableContainer table = aTable(ExampleTable.class).with(aRow(42)).build(context);

        final TableContainer expected = sb.table(
                sb.row(
                        sb.property(ExampleTable.PROP_A,"42"),
                        sb.property(ExampleTable.PROP_B,"42")
                )
        );

        assertThat(table, is(expected));
    }
}
