package de.brands4friends.daleq.internal.dbunit;

import org.dbunit.database.IDatabaseConnection;

public interface ConnectionFactory {
    IDatabaseConnection createConnection();
}
