package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.exception.NoDataFoundException;
import com.shelberg.entity.Country;
import com.shelberg.search.CountrySearchQuery;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
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
        Country country = jdbcCountryDao.getCountryStatistics(code);

        Assert.assertEquals("AGO", country.getCode());
        Assert.assertEquals("Angola", country.getName());
        Assert.assertEquals("Europe", country.getContinent());
        Assert.assertEquals("Central Africa", country.getRegion());
        Assert.assertEquals(1246700.0, country.getSurfaceArea(), 0.01);
        Assert.assertEquals(12878000, (int) country.getPopulation());
        Assert.assertNotEquals(0, country.getLanguageList().size());

    }


    @Test
    public void searchByName() {

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
        String language = "Chichewa";

        List<Country> countries = jdbcCountryDao.getCountriesByLanguage(language);

        Assert.assertNotNull(countries);

        Country country = countries.get(0);
        Assert.assertEquals(1, countries.size());
        Assert.assertEquals("ALB", country.getCode());
        Assert.assertEquals("Albania", country.getName());
        Assert.assertEquals("Europe", country.getContinent());
        Assert.assertEquals("Southern Europe", country.getRegion());
        Assert.assertEquals(28748.0, country.getSurfaceArea(), 0.00);
        Assert.assertEquals(1912, (int) country.getIndepYear());
        Assert.assertEquals(3401200, (int) country.getPopulation());
        Assert.assertEquals(71.5999984741211, country.getLifeExpectancy(), 0.00);
        Assert.assertEquals("Republic", country.getGovernmentForm());
        Assert.assertEquals("Rexhep Mejdani", country.getHeadOfState());
        Assert.assertEquals("Tirana", country.getCapital());

        String countryCriteriaQuery = jdbcCountryDao.getCountryCriteriaQuery(countrySearchQuery);
        System.out.println(countryCriteriaQuery);
    }

    @Test
    public void add() {
        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);

        String code = "UKR";
        Country country = new Country();
        country.setName("Ukraine");
        country.setContinent("Europe");
        country.setRegion("Central Europe");
        country.setSurfaceArea(300.00);
        country.setIndepYear(1991);
        country.setPopulation(10000);
        country.setLifeExpectancy(75.00);
        country.setGovernmentForm("Republic");
        country.setHeadOfState("Volodymyr Zelemskyi");
        country.setCapital("Qandahar");
        country.setCode(code);
        country.setCode2("UKR");

        jdbcCountryDao.add(country);

        Country countryAfter = jdbcCountryDao.getCountryByCode(code);
        Assert.assertEquals(country, countryAfter);

    }

    @Test(expected = NoDataFoundException.class)
    public void delete() {
        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);
        String code = "ABW";
        Country country = jdbcCountryDao.getCountryByCode(code);
        Assert.assertNotNull(country);
        jdbcCountryDao.delete(code);
        jdbcCountryDao.getCountryByCode(code);
    }

    @Test
    public void update() {
        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);
        Country countryBefore = jdbcCountryDao.getCountryByCode("ALB");

        Country country = new Country();
        country.setName("Ukraine");
        country.setContinent("Europe");
        country.setRegion("Central Europe");
        country.setSurfaceArea(300.00);
        country.setIndepYear(1991);
        country.setPopulation(10000);
        country.setLifeExpectancy(75.00);
        country.setGovernmentForm("Republic");
        country.setHeadOfState("Volodymyr Zelemskyi");
        country.setCapital("Qandahar");
        country.setCode2("UKR");
        country.setCode("AIA");

        jdbcCountryDao.update(country);

        Country countryAfter = jdbcCountryDao.getCountryByCode("AIA");
        Assert.assertNotEquals(countryBefore, countryAfter);

    }
}
