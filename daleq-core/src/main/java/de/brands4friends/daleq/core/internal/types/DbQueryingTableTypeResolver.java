package de.brands4friends.daleq.core.internal.types;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import de.brands4friends.daleq.core.Daleq;
import de.brands4friends.daleq.core.DaleqBuildException;
import de.brands4friends.daleq.core.FieldType;
import de.brands4friends.daleq.core.FieldTypeReference;
import de.brands4friends.daleq.core.TableType;
import de.brands4friends.daleq.core.TableTypeReference;
import de.brands4friends.daleq.core.TemplateValue;
import de.brands4friends.daleq.core.internal.dbunit.DataTypeMapping;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class DbQueryingTableTypeResolver implements TableTypeResolver {

    private final DataSource dataSource;

    private String catalog = null;
    private String schema = null;

    public DbQueryingTableTypeResolver(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public boolean canResolve(TableTypeReference reference) {
        return reference instanceof NamedTableTypeReference;
    }

    @Override
    public TableType resolve(TableTypeReference reference) {
        if (!canResolve(reference)) {
            throw new IllegalArgumentException("Cannot resolve the reference + " + reference);
        }
        final NamedTableTypeReference namedRef = (NamedTableTypeReference) reference;

        try {
            final DatabaseMetaData metaData = dataSource.getConnection().getMetaData();

            final ResultSet rs = metaData.getColumns(catalog, schema, namedRef.getName(), null);

            final List<FieldType> fieldTypes = Lists.newArrayList();
            while (rs.next()) {
                final String columnName = rs.getString("COLUMN_NAME");
                final int type = rs.getInt("DATA_TYPE");
                final DataType dbUnitDataType = DataType.forSqlType(type);
                final de.brands4friends.daleq.core.DataType daleqDataType = DataTypeMapping.toDaleq(dbUnitDataType);
                final FieldTypeReference fieldRef = Daleq.fd(daleqDataType).name(columnName);
                final FieldType fieldType = new FieldTypeImpl(
                        columnName,
                        daleqDataType,
                        Optional.<TemplateValue>absent(),
                        fieldRef);

                fieldTypes.add(fieldType);
            }
            // TODO handle if not exists!

            return new TableTypeImpl(namedRef.getName(), fieldTypes);

        } catch (SQLException e) {
            throw new DaleqBuildException(e);
        } catch (DataTypeException e) {
            throw new DaleqBuildException(e);
        }
    }
}
