package com.alexa.worldservice.dao.jdbc;

import com.shelberg.entity.Country;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class JdbcCountryDaoTest {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    @BeforeClass
    public static void setup() {
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


    @Test
    public void getCountriesByLanguage() {
        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);
        String language = "Chichewa";

        List<Country> countries = jdbcCountryDao.getCountriesByLanguage(language);

        Assert.assertNotNull(countries);
        Assert.assertEquals(1,countries.size());
        Country country = countries.get(0);
        Assert.assertEquals("ALB",country.getCode());
        Assert.assertEquals("Albania",country.getName());
        Assert.assertEquals("Europe",country.getContinent());
        Assert.assertEquals("Southern Europe", country.getRegion());
        Assert.assertEquals(28748.0,country.getSurfaceArea(),0.00);
        Assert.assertEquals(1912, country.getIndepYear());
        Assert.assertEquals(3401200, country.getPopulation());
        Assert.assertEquals(71.5999984741211,country.getLifeExpectancy(), 0.00);
        Assert.assertEquals("Republic",country.getGovernmentForm());
        Assert.assertEquals("Rexhep Mejdani", country.getHeadOfState());
        Assert.assertEquals("Shqipria", country.getCapital());




    }
}