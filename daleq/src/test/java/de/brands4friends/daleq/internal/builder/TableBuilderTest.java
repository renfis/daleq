package de.brands4friends.daleq.internal.builder;

import org.junit.Test;

import de.brands4friends.daleq.internal.container.TableContainer;

public class TableBuilderTest {

    @Test
    public void test(){
        Context context = new SimpleContext();
        TableContainer table =  TableBuilder.aTable(ExampleTable.class).build(context);
        
    }
}
