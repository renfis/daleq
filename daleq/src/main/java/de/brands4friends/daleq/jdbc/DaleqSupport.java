package de.brands4friends.daleq.jdbc;

import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;

import de.brands4friends.daleq.internal.container.SchemaContainer;
import de.brands4friends.daleq.internal.container.TableContainer;

public interface DaleqSupport {

    IDatabaseConnection createDatabaseConnection();
    void insertIntoDatabase(SchemaContainer schema) throws DatabaseUnitException, SQLException;
    void insertIntoDatabase(TableContainer... tables);
    void assertTableContentInDatabase(SchemaContainer expectedSchema, String tableName, IDatabaseConnection databaseConnection);
    void assertTableContentInDatabaseIgnoreCols(final SchemaContainer expectedSchema,
                                                       final String tableName,
                                                       final IDatabaseConnection databaseConnection);
}
