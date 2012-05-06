package de.brands4friends.daleq.jdbc;

import de.brands4friends.daleq.Table;

public interface DaleqSupport {

    void insertIntoDatabase(Table... tables);
// TODO Rework interface
//    IDatabaseConnection createDatabaseConnection();
//    void assertTableContentInDatabase(SchemaContainer expectedSchema, String tableName, IDatabaseConnection databaseConnection);
//    void assertTableContentInDatabaseIgnoreCols(final SchemaContainer expectedSchema,
//                                                       final String tableName,
//                                                       final IDatabaseConnection databaseConnection);
}
