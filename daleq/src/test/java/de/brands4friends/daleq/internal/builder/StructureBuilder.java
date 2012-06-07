package de.brands4friends.daleq.internal.builder;

import java.util.Arrays;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.FieldDef;
import de.brands4friends.daleq.internal.container.PropertyContainer;
import de.brands4friends.daleq.internal.container.RowContainer;
import de.brands4friends.daleq.internal.container.TableContainer;
import de.brands4friends.daleq.internal.structure.TableStructure;

public class StructureBuilder {

    public final static class PropertyContainerBean {

        FieldDef fieldDef;
        String value;
        private PropertyContainerBean(final FieldDef fieldDef, final String value) {
            this.fieldDef = fieldDef;
            this.value = value;
        }

    }
    private final TableStructure tableStructure;

    public StructureBuilder(final TableStructure tableStructure) {
        this.tableStructure = tableStructure;
    }

    public TableContainer table(RowContainer ... rowContainers){
        return new TableContainer(tableStructure,Arrays.asList(rowContainers));
    }

    public RowContainer row(PropertyContainerBean... props) {
        return new RowContainer(tableStructure, Lists.transform(
                Arrays.asList(props),
                new Function<PropertyContainerBean, PropertyContainer>() {
                    @Override
                    public PropertyContainer apply(final PropertyContainerBean input) {
                        return new PropertyContainer(tableStructure.findStructureByDef(input.fieldDef), input.value);
                    }
                }));
    }

    public PropertyContainerBean property(FieldDef fieldDef, String value) {
        return new PropertyContainerBean(fieldDef, value);
    }
}
