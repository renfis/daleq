package de.brands4friends.daleq.internal.builder;

import java.util.Arrays;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.PropertyDef;
import de.brands4friends.daleq.internal.container.PropertyContainer;
import de.brands4friends.daleq.internal.container.RowContainer;
import de.brands4friends.daleq.internal.container.TableContainer;
import de.brands4friends.daleq.internal.structure.TableStructure;

public class StructureBuilder {

    public final static class PropertyContainerBean {

        PropertyDef propertyDef;
        String value;
        private PropertyContainerBean(final PropertyDef propertyDef, final String value) {
            this.propertyDef = propertyDef;
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
                        return new PropertyContainer(tableStructure.findStructureByDef(input.propertyDef), input.value);
                    }
                }));
    }

    public PropertyContainerBean property(PropertyDef propertyDef, String value) {
        return new PropertyContainerBean(propertyDef, value);
    }
}
