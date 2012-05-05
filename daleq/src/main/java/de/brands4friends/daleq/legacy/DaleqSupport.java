package de.brands4friends.daleq.legacy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.assertion.DbUnitAssert;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.brands4friends.daleq.legacy.dbunit.DbUnitDataSetConverter;
import de.brands4friends.daleq.legacy.schema.Daleq;
import de.brands4friends.daleq.legacy.schema.Schema;
import de.brands4friends.daleq.legacy.schema.Table;

public class DaleqSupport {

    private final DataSource dataSource;

    public DaleqSupport(DataSource dataSource) {
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
     * @throws RuntimeException
     *          if DbUnit denies the creation of the IDatabaseConnection
     */
    public final IDatabaseConnection createDatabaseConnection() {

        try {
            Connection conn = DataSourceUtils.getConnection(dataSource);
            DatabaseConnection databaseConnection= new DatabaseConnection(conn);
            databaseConnection.getConfig().setProperty(
                "http://www.dbunit.org/properties/datatypeFactory", new HsqldbDataTypeFactory());

            return databaseConnection;
        } catch (DatabaseUnitException e) {
                throw new RuntimeException(e);
        }
    }

    /**
     * Insert a Schema into the Database.
     * <p/>
     * The insertion respects the current transaction context, hence if they are written in an active transaction, they
     * are properly roled back.
     */
    public final void insertIntoDatabase(Schema schema) throws DatabaseUnitException, SQLException {
        IDataSet dbUnitDataset = new DbUnitDataSetConverter().convertTo(schema);


        DatabaseOperation.INSERT.execute(createDatabaseConnection(), dbUnitDataset);
    }

    /**
     * Inserts the given tables into the database.
     * <p/>
     * The insertion respects the current transaction context, hence if they are written in an active transaction, they
     * are properly roled back.
     */
    public final void insertIntoDatabase(Table... tables)  {
        try {
            insertIntoDatabase(Daleq.schema(tables));
        } catch (DatabaseUnitException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Asserts that the content of the a table is the same as in the given Dalec Schema.
     */
    public void assertTableContentInDatabase(Schema expectedSchema, String tableName, IDatabaseConnection databaseConnection) {

        try {
        IDataSet actualDatabase = databaseConnection.createDataSet();

        DbUnitDataSetConverter converter = new DbUnitDataSetConverter();
        IDataSet expectedDatabase = converter.convertTo(expectedSchema);

        ITable expectedAccountTable = expectedDatabase.getTable(tableName);
        ITable actualAccountTable = actualDatabase.getTable(tableName);

        DbUnitAssert dbUnitAssert = new DbUnitAssert();
        dbUnitAssert.assertEquals(expectedAccountTable, actualAccountTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataSetException e) {
            throw new RuntimeException(e);
        } catch (DatabaseUnitException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Asserts that the content of the a table is the same as in the given Dalec Schema.
     */
    public void assertTableContentInDatabaseIgnoreCols(final Schema expectedSchema,
                                                       final String tableName,
                                                       final IDatabaseConnection databaseConnection) {

        try {
            final IDataSet actualDatabase = databaseConnection.createDataSet();

            final DbUnitDataSetConverter converter = new DbUnitDataSetConverter();
            final IDataSet expectedDatabase = converter.convertTo(expectedSchema);

            final ITable expectedAccountTable = expectedDatabase.getTable(tableName);
            final ITable actualAccountTable = actualDatabase.getTable(tableName);

            final Set<String> expectedTableColumnNames = columnNames(expectedAccountTable.getTableMetaData().getColumns());
            final Set<String> actualTableColumnNames = columnNames(actualAccountTable.getTableMetaData().getColumns());

            final Sets.SetView<String> ignoredColumnNames = Sets.difference(actualTableColumnNames, expectedTableColumnNames);

            final DbUnitAssert dbUnitAssert = new DbUnitAssert();
            dbUnitAssert.assertEqualsIgnoreCols(expectedAccountTable, actualAccountTable,
                    ignoredColumnNames.toArray(new String[ignoredColumnNames.size()]));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataSetException e) {
            throw new RuntimeException(e);
        } catch (DatabaseUnitException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extract column names into a set from the given array of columns.
     *
     * @param columns to get the column names from.
     * @return  set of names of the columns.
     */
    private Set<String> columnNames(final Column[] columns) {
        return Sets.newHashSet(Collections2.transform(Lists.<Column>newArrayList(columns), new Function<Column, String>() {
            public String apply(final Column column) {
                return column != null ? column.getColumnName() : null;
            }
        }));
    }
}
