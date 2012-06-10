package de.brands4friends.daleq.internal.builder;

import java.util.Arrays;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.FieldContainer;
import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.RowContainer;
import de.brands4friends.daleq.TableContainer;
import de.brands4friends.daleq.internal.types.FieldType;
import de.brands4friends.daleq.internal.types.TableType;

public class StructureBuilder {

    public static final class PropertyContainerBean {

        private final FieldDef fieldDef;
        private final String value;

        private PropertyContainerBean(final FieldDef fieldDef, final String value) {
            this.fieldDef = fieldDef;
            this.value = value;
        }

    }

    private final TableType tableType;

    public StructureBuilder(final TableType tableType) {
        this.tableType = tableType;
    }

    public TableContainer table(final RowContainer... rowContainers) {
        return new TableContainerImpl(tableType.getName(), Arrays.asList(rowContainers));
    }

    public RowContainer row(final PropertyContainerBean... props) {
        return new RowContainerImpl(Lists.transform(
                Arrays.asList(props),
                new Function<PropertyContainerBean, FieldContainer>() {
                    @Override
                    public FieldContainer apply(final PropertyContainerBean input) {
                        final FieldType fieldType = tableType.findStructureByDef(input.fieldDef);
                        return new FieldContainerImpl(fieldType.getName(), input.value);
                    }
                }));
    }

    @SuppressWarnings("PMD.AccessorClassGeneration") // its the intention of this test helper to act like a factory.
    // if this method would be static, it would also be awkward.
    public PropertyContainerBean field(final FieldDef fieldDef, final String value) {
        return new PropertyContainerBean(fieldDef, value);
    }
}
