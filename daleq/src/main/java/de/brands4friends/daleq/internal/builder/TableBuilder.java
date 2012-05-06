package de.brands4friends.daleq.internal.builder;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.Row;
import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.internal.container.RowContainer;
import de.brands4friends.daleq.internal.container.TableContainer;
import de.brands4friends.daleq.internal.structure.TableStructure;
import de.brands4friends.daleq.internal.structure.TableStructureFactory;

public class TableBuilder implements Table {

    private final TableStructure tableStructure;
    private final List<Row> rows;

    public TableBuilder(final TableStructure tableStructure) {
        this.tableStructure = tableStructure;
        this.rows = Lists.newArrayList();
    }

    @Override
    public Table with(Row... rows) {
        this.rows.addAll(Arrays.asList(rows));
        return this;
    }

    @Override
    public Table withSomeRows(Iterable<Object> substitutes) {
        // TODO
        return this;
    }

    @Override
    public TableContainer build(final Context context){
        List<RowContainer> rowContainers = Lists.transform(rows,new Function<Row, RowContainer>() {
            @Override
            public RowContainer apply(final Row row) {
                return row.build(context,tableStructure);
            }
        });
        return new TableContainer(tableStructure, rowContainers);
    }

    public static <T> TableBuilder aTable(Class<T> fromClass) {
        final TableStructure tableStructure = new TableStructureFactory().create(fromClass);
        return new TableBuilder(tableStructure);
    }
}
