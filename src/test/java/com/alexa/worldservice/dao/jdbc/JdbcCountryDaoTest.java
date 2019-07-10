package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.CountryLanguageStatistics;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        String countryName = "Aruba";

        // Execute
        CountryLanguageStatistics languageStatistics = jdbcCountryDao.getStatistics(countryName);

        Assert.assertEquals("ABW", languageStatistics.getCode());
        Assert.assertEquals("Aruba", languageStatistics.getName());
        Assert.assertEquals("North America", languageStatistics.getContinent());
        Assert.assertEquals("Caribbean", languageStatistics.getRegion());
        Assert.assertEquals("French", languageStatistics.getLanguage());
        Assert.assertEquals(193, languageStatistics.getSurfaceArea(), 0.01);
        
    }
}