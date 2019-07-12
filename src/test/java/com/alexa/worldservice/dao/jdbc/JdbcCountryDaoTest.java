package com.alexa.worldservice.dao.jdbc;

import com.shelberg.entity.Country;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JdbcCountryDaoTest {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    @Before
    public void setup() {
        config.setUsername("sa");
        config.setPassword("");
        config.setJdbcUrl("jdbc:h2:mem:test;INIT=runscript from 'classpath:init.sql'");
        config.setDriverClassName("org.h2.Driver");
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        dataSource = new HikariDataSource(config);
    }

    @Test
    public void getStatistics() {
        // Prepare
        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);
        String countryName = "Angola";
        String language = "French";

        // Execute
        Country country = jdbcCountryDao.getCountry(countryName);

        Assert.assertEquals("AGO", country.getCode());
        Assert.assertEquals("Angola", country.getName());
        Assert.assertEquals("Africa", country.getContinent());
        Assert.assertEquals("Central Africa", country.getRegion());
        Assert.assertEquals(1246700.0, country.getSurfaceArea(), 0.01);
        Assert.assertEquals(12878000,country.getPopulation());
        Assert.assertNotEquals(0, country.getLanguageList().size());

    }
}