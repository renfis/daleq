package de.brands4friends.daleq.jdbc.dbunit;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;

import com.google.common.base.Preconditions;

public class SimpleConnectionFactory implements ConnectionFactory {

    private DataSource dataSource;

    private IDataTypeFactory dataTypeFactory = new HsqldbDataTypeFactory();

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataTypeFactory(final IDataTypeFactory dataTypeFactory) {
        this.dataTypeFactory = dataTypeFactory;
    }

    @Override
    public IDatabaseConnection createConnection() {
        Preconditions.checkNotNull(dataSource,"dataSource is null.");
        try {
            final Connection conn = dataSource.getConnection();
            final DatabaseConnection databaseConnection = new DatabaseConnection(conn);
            databaseConnection.getConfig().setProperty(
                    "http://www.dbunit.org/properties/datatypeFactory", dataTypeFactory);
            return databaseConnection;
        } catch (DatabaseUnitException e) {
            throw new DaleqException(e);
        } catch (SQLException e) {
            throw new DaleqException(e);
        }
    }
}
