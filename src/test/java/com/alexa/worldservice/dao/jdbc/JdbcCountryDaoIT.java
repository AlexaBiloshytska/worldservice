package com.alexa.worldservice.dao.jdbc;

import com.shelberg.entity.Country;
import com.shelberg.search.CountrySearchQuery;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;


public class JdbcCountryDaoIT {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    @BeforeClass
    public static void setup() {
        config.setUsername("sa");
        config.setPassword("");
        config.setJdbcUrl("jdbc:h2:mem:test;INIT=runscript from 'classpath:init.sql'");
        config.setDriverClassName("org.h2.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    @Test
    public void getStatistics() {
        // Prepare
        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);
        String code = "AGO";

        // Execute
        Country country = jdbcCountryDao.getCountry(code);

        Assert.assertEquals("AGO", country.getCode());
        Assert.assertEquals("Angola", country.getName());
        Assert.assertEquals("Europe", country.getContinent());
        Assert.assertEquals("Central Africa", country.getRegion());
        Assert.assertEquals(1246700.0, country.getSurfaceArea(), 1);
        Assert.assertEquals(12878000, (int)country.getPopulation());
        Assert.assertNotEquals(0, country.getLanguageList().size());
    }


    @Test
    public void getCountriesByLanguage() {
        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);
        String language = "Chichewa";

        List<Country> countries = jdbcCountryDao.getCountriesByLanguage(language);

        Assert.assertNotNull(countries);

        Assert.assertEquals(1, countries.size());
        Assert.assertEquals("ALB", countries.get(0).getCode());
        Assert.assertEquals("Albania", countries.get(0).getName());
        Assert.assertEquals("Europe", countries.get(0).getContinent());
        Assert.assertEquals("Southern Europe", countries.get(0).getRegion());
        Assert.assertEquals(28748.0, countries.get(0).getSurfaceArea(), 0.00);
        Assert.assertEquals(1912, (int)countries.get(0).getIndepYear());
        Assert.assertEquals(3401200, (int)countries.get(0).getPopulation());
        Assert.assertEquals(71.5, countries.get(0).getLifeExpectancy(), 0.1);
        Assert.assertEquals("Republic", countries.get(0).getGovernmentForm());
        Assert.assertEquals("Rexhep Mejdani", countries.get(0).getHeadOfState());
        Assert.assertEquals("Tirana", countries.get(0).getCapital());

    }

    @Test
    public void searchByCriteria() {
        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);

        int population = 100;
        int page = 1;
        int limit = 5;
        String name = "ang";
        String continent = "Europe";
        CountrySearchQuery countrySearchQuery = new CountrySearchQuery.Builder(name, continent)
                .setPopulation(population)
                .setLimit(limit)
                .setPage(page)
                .build();

        List<Country> countries = jdbcCountryDao.searchByCriteria(countrySearchQuery);

        Assert.assertNotNull(countries);
        Assert.assertEquals(2, countries.size());
    }
}