package de.brands4friends.daleq.examples;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcProductDao extends JdbcDaoSupport implements ProductDao {

    public JdbcProductDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public Product findById(final long id) {
        return getJdbcTemplate().query(
                "select ID,NAME,SIZE,PRICE from PRODUCT",
                new ResultSetExtractor<Product>() {
                    @Override
                    public Product extractData(final ResultSet rs) throws SQLException, DataAccessException {

                        Product p = new Product();
                        p.setId(rs.getLong("ID"));
                        p.setName(rs.getString("NAME"));
                        p.setSize(rs.getString("SIZE"));
                        p.setPrice(rs.getBigDecimal("PRICE"));

                        return p;
                    }
                });
    }
}
