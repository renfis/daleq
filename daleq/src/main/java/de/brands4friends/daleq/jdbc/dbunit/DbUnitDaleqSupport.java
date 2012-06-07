package de.brands4friends.daleq.jdbc.dbunit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.internal.builder.Context;
import de.brands4friends.daleq.internal.builder.SimpleContext;
import de.brands4friends.daleq.internal.container.SchemaContainer;
import de.brands4friends.daleq.internal.container.TableContainer;
import de.brands4friends.daleq.jdbc.DaleqSupport;

public class DbUnitDaleqSupport implements DaleqSupport {

    private final DataSource dataSource;

    private IDataSetFactory dataSetFactory = new FlatXmlIDataSetFactory();
    private IDataTypeFactory dataTypeFactory = new HsqldbDataTypeFactory();
    private Context context = new SimpleContext();

    public DbUnitDaleqSupport(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Returns a DatabaseConnection which is aware of Spring's Transaction Management.
     * <p/>
     * As a matter of fact this works if and only if we are already in an active Transaction due to the way
     * Spring's Transaction Manager works. Hence we have to create a new DbUnit Database Connection each time
     * we are going to insert data in the db.
     *
     * @return a transaction aware connection to the database.
     * @throws RuntimeException if DbUnit denies the creation of the IDatabaseConnection
     */
    private IDatabaseConnection createDatabaseConnection() {

        try {
            // TODO this introduces the spring-jdbc dependency. daleq should be free of spring per se.
//            final Connection conn = DataSourceUtils.getConnection(dataSource);
            final Connection conn = dataSource.getConnection();
            final DatabaseConnection databaseConnection = new DatabaseConnection(conn);
            databaseConnection.getConfig().setProperty(
                    "http://www.dbunit.org/properties/datatypeFactory", dataTypeFactory);
            return databaseConnection;
        } catch (DatabaseUnitException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts the given tables into the database.
     * <p/>
     * The insertion respects the current transaction context, hence if they are written in an active transaction, they
     * are properly roled back.
     */
    @Override
    public final void insertIntoDatabase(Table... tables) {
        try {
            final List<TableContainer> tableContainers = Lists.transform(Arrays.asList(tables), new Function<Table, TableContainer>() {
                @Override
                public TableContainer apply(final Table table) {
                    return table.build(context);
                }
            });
            insertIntoDatabase(new SchemaContainer(tableContainers));

        } catch (DatabaseUnitException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertIntoDatabase(SchemaContainer schema) throws DatabaseUnitException, SQLException {
        final IDataSet dbUnitDataset = dataSetFactory.create(schema);
        DatabaseOperation.INSERT.execute(createDatabaseConnection(), dbUnitDataset);
    }
}
