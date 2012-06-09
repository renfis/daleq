package de.brands4friends.daleq.examples;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcProductDao extends JdbcDaoSupport implements ProductDao {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = new RowMapper<Product>() {
        @Override
        public Product mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            Product p = new Product();
            p.setId(rs.getLong("ID"));
            p.setName(rs.getString("NAME"));
            p.setSize(rs.getString("SIZE"));
            p.setPrice(rs.getBigDecimal("PRICE"));

            return p;
        }
    };

    public JdbcProductDao(final DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public Product findById(final long id) {
        return getJdbcTemplate().queryForObject(
                "select ID,NAME,SIZE,PRICE from PRODUCT",
                PRODUCT_ROW_MAPPER);
    }

    @Override
    public List<Product> findBySize(final String size) {
        return getJdbcTemplate().query("select ID,NAME,SIZE,PRICE from PRODUCT where SIZE = ?",PRODUCT_ROW_MAPPER,size);
    }
}
