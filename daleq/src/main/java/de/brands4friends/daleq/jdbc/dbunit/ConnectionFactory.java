package de.brands4friends.daleq.jdbc.dbunit;

import org.dbunit.database.IDatabaseConnection;

public interface ConnectionFactory {
    IDatabaseConnection createConnection();
}
