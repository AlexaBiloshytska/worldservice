package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.exception.NoDataFoundException;
import com.shelberg.entity.City;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Before;
import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;


public class JdbcCityDaoTestIT {
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
    public void searchCityByCriteria() {
        JdbcCityDao jdbcCityDao = new JdbcCityDao(dataSource);

        CitySearchQuery citySearchQuery = new CitySearchQuery.Builder("a1", "iv", "Europe")
                .countryRequired(true)
                .populationRequired(true)
                .countryPopulationRequired(false).build();

        List<SearchCity> cities = jdbcCityDao.searchCityByCriteria(citySearchQuery);

        Assert.assertNotNull(cities);
        String country = "al";
        String name = "iv";
        String continent = "Europe";

        Assert.assertNotNull(cities);
    }

    @Test
    public void add() {
        JdbcCityDao jdbcCityDao = new JdbcCityDao(dataSource);

        City city = new City();
        city.setName("Nicaragua");
        city.setCountryCode("NK");
        city.setDistrict("Spain");
        city.setPopulation(4500000);

        jdbcCityDao.add(city);

        City cityById = jdbcCityDao.getCityById(6);
        city.setId(6);
        Assert.assertEquals(city, cityById);
    }

    @Test(expected = NoDataFoundException.class)
    public void delete() {
        JdbcCityDao jdbcCityDao = new JdbcCityDao(dataSource);
        int id = 1;
        City beforeDeletion = jdbcCityDao.getCityById(id);
        jdbcCityDao.delete(id);
        City afterDeletion = jdbcCityDao.getCityById(id);

        Assert.assertNotEquals(beforeDeletion,afterDeletion);
    }

    @Test
    public void update() {
        JdbcCityDao jdbcCityDao = new JdbcCityDao(dataSource);

        int id = 5;
        City cityBeforeUpdate = jdbcCityDao.getCityById(id);

        cityBeforeUpdate.setPopulation(0);

        jdbcCityDao.update(cityBeforeUpdate);

        City cityAfterUpdate = jdbcCityDao.getCityById(id);

        Assert.assertEquals(cityBeforeUpdate, cityAfterUpdate);

    }
}