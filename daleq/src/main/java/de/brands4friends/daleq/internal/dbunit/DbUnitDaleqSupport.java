package de.brands4friends.daleq.internal.dbunit;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.assertion.DbUnitAssert;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.Context;
import de.brands4friends.daleq.DaleqSupport;
import de.brands4friends.daleq.SchemaContainer;
import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.TableContainer;
import de.brands4friends.daleq.internal.builder.SchemaContainerImpl;
import de.brands4friends.daleq.internal.builder.SimpleContext;

public class DbUnitDaleqSupport implements DaleqSupport {

    private IDataSetFactory dataSetFactory = new FlatXmlIDataSetFactory();
    private ConnectionFactory connectionFactory;
    private DatabaseOperation insertOperation = DatabaseOperation.INSERT;

    private final Context context = new SimpleContext();
    private final DbUnitAssert dbUnitAssert = new DbUnitAssert();

    public void setConnectionFactory(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setDataSetFactory(final IDataSetFactory dataSetFactory) {
        this.dataSetFactory = dataSetFactory;
    }

    public void setInsertOperation(final DatabaseOperation insertOperation) {
        this.insertOperation = insertOperation;
    }

    /**
     * Returns a DatabaseConnection which is aware of Spring's Transaction Management.
     * <p/>
     * As a matter of fact this works if and only if we are already in an active Transaction due to the way
     * Spring's Transaction Manager works. Hence we have to create a new DbUnit Database Connection each time
     * we are going to insert data in the db.
     *
     * @return a transaction aware connection to the database.
     * @throws DaleqException if DbUnit denies the creation of the IDatabaseConnection
     */
    private IDatabaseConnection createDatabaseConnection() {
        Preconditions.checkNotNull(connectionFactory, "connectionFactory is null.");
        return connectionFactory.createConnection();
    }

    /**
     * Inserts the given tables into the database.
     * <p/>
     * The insertion respects the current transaction context, hence if they are written in an active transaction, they
     * are properly roled back.
     */
    @Override
    public final void insertIntoDatabase(final Table... tables) {
        try {
            insertIntoDatabase(toSchemaContainer(tables));

        } catch (DatabaseUnitException e) {
            throw new DaleqException(e);
        } catch (SQLException e) {
            throw new DaleqException(e);
        }
    }

    private SchemaContainer toSchemaContainer(final Table... tables) {
        final List<TableContainer> tableContainers = Lists.transform(
                Arrays.asList(tables),
                new Function<Table, TableContainer>() {
                    @Override
                    public TableContainer apply(final Table table) {
                        return table.build(context);
                    }
                });
        return new SchemaContainerImpl(tableContainers);
    }

    @Override
    public void assertTableInDatabase(final Table table) {
        Preconditions.checkNotNull(table);
        try {
            final SchemaContainer schemaContainer = toSchemaContainer(table);
            final String tableName = schemaContainer.getTables().get(0).getName();

            final IDataSet expectedDataSet = dataSetFactory.create(schemaContainer);
            final ITable expectedTable = expectedDataSet.getTable(tableName);
            final IDataSet actualDataSet = createDatabaseConnection().createDataSet();
            final ITable actualTable = actualDataSet.getTable(tableName);

            dbUnitAssert.assertEquals(expectedTable, actualTable);

        } catch (DataSetException e) {
            throw new DaleqException(e);
        } catch (SQLException e) {
            throw new DaleqException(e);
        } catch (DatabaseUnitException e) {
            throw new DaleqException(e);
        }
    }

    private void insertIntoDatabase(final SchemaContainer schema) throws DatabaseUnitException, SQLException {
        final IDataSet dbUnitDataset = dataSetFactory.create(schema);
        insertOperation.execute(createDatabaseConnection(), dbUnitDataset);
    }
}
