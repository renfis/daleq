package de.brands4friends.daleq.jdbc.dbunit;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import de.brands4friends.daleq.Table;
import de.brands4friends.daleq.internal.builder.Context;
import de.brands4friends.daleq.internal.builder.SimpleContext;
import de.brands4friends.daleq.internal.container.SchemaContainer;
import de.brands4friends.daleq.internal.container.TableContainer;
import de.brands4friends.daleq.jdbc.DaleqSupport;

public class DbUnitDaleqSupport implements DaleqSupport{

    private IDataSetFactory dataSetFactory = new FlatXmlIDataSetFactory();
    private ConnectionFactory connectionFactory;
    private DatabaseOperation insertOperation = DatabaseOperation.INSERT;

    private final Context context = new SimpleContext();

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
            final List<TableContainer> tableContainers = Lists.transform(
                    Arrays.asList(tables),
                    new Function<Table, TableContainer>(){
                        @Override
                        public TableContainer apply(final Table table) {
                            return table.build(context);
                        }
                    });
            insertIntoDatabase(new SchemaContainer(tableContainers));

        } catch (DatabaseUnitException e) {
            throw new DaleqException(e);
        } catch (SQLException e) {
            throw new DaleqException(e);
        }
    }

    private void insertIntoDatabase(final SchemaContainer schema) throws DatabaseUnitException, SQLException {
        final IDataSet dbUnitDataset = dataSetFactory.create(schema);
        insertOperation.execute(createDatabaseConnection(), dbUnitDataset);
    }
}
