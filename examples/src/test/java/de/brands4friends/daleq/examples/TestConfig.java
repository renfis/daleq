package de.brands4friends.daleq.examples;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import de.brands4friends.daleq.jdbc.DaleqSupport;
import de.brands4friends.daleq.jdbc.dbunit.ConnectionFactory;
import de.brands4friends.daleq.jdbc.dbunit.DbUnitDaleqSupport;
import de.brands4friends.daleq.jdbc.dbunit.SimpleConnectionFactory;

@Configuration
public class TestConfig {

    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder().addScript("schema.sql").build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(final DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DaleqSupport daleqSupport(final ConnectionFactory connectionFactory){
        final DbUnitDaleqSupport dbUnitDaleqSupport = new DbUnitDaleqSupport();
        dbUnitDaleqSupport.setConnectionFactory(connectionFactory);
        return dbUnitDaleqSupport;
    }

    @Bean
    public ConnectionFactory connectionFactory(final DataSource dataSource){
        final SimpleConnectionFactory connectionFactory = new SimpleConnectionFactory();
        connectionFactory.setDataSource(dataSource);
        return connectionFactory;
    }
}
