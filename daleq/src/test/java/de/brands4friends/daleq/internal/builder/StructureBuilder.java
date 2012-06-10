package de.brands4friends.daleq.internal.builder;

import java.util.Arrays;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.internal.container.FieldContainer;
import de.brands4friends.daleq.internal.container.RowContainer;
import de.brands4friends.daleq.internal.container.TableContainer;
import de.brands4friends.daleq.internal.structure.FieldStructure;
import de.brands4friends.daleq.internal.structure.TableStructure;

public class StructureBuilder {

    public static final class PropertyContainerBean {

        private final FieldDef fieldDef;
        private final String value;

        private PropertyContainerBean(final FieldDef fieldDef, final String value) {
            this.fieldDef = fieldDef;
            this.value = value;
        }

    }

    private final TableStructure tableStructure;

    public StructureBuilder(final TableStructure tableStructure) {
        this.tableStructure = tableStructure;
    }

    public TableContainer table(final RowContainer... rowContainers) {
        return new TableContainer(tableStructure, Arrays.asList(rowContainers));
    }

    public RowContainer row(final PropertyContainerBean... props) {
        return new RowContainer(tableStructure, Lists.transform(
                Arrays.asList(props),
                new Function<PropertyContainerBean, FieldContainer>() {
                    @Override
                    public FieldContainer apply(final PropertyContainerBean input) {
                        final FieldStructure fieldStructure = tableStructure.findStructureByDef(input.fieldDef);
                        return new FieldContainer(fieldStructure.getName(), input.value);
                    }
                }));
    }

    @SuppressWarnings("PMD.AccessorClassGeneration") // its the intention of this test helper to act like a factory.
    // if this method would be static, it would also be awkward.
    public PropertyContainerBean field(final FieldDef fieldDef, final String value) {
        return new PropertyContainerBean(fieldDef, value);
    }
}
