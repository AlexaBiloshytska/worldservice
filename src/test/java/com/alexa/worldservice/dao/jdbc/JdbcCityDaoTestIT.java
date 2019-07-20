package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.City;
import com.alexa.worldservice.entity.SearchCity;
import com.alexa.worldservice.exception.NoDataFoundException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JdbcCityDaoTestIT {
    private static HikariDataSource dataSource;

    @Before
    public void setup() {
        HikariConfig config = new HikariConfig();
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

        String country = "al";
        String name = "iv";
        String continent = "Europe";
        List<SearchCity> cities = jdbcCityDao.searchCityByCriteria(true, true, false, country, name, continent);

        Assert.assertNotNull(cities);
    }

    @Test
    public void add() {
        JdbcCityDao jdbcCityDao = new JdbcCityDao(dataSource);

        City city = new City();
        city.setId(9999999);
        city.setName("Nicaragua");
        city.setCountryCode("NK");
        city.setDistrict("Spain");
        city.setPopulation(4500000);

        jdbcCityDao.add(city);

        City cityById = jdbcCityDao.getCityById(9999999);
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

        int id = 34;
        City cityBeforeUpdate = jdbcCityDao.getCityById(id);

        cityBeforeUpdate.setPopulation(0);

        jdbcCityDao.update(cityBeforeUpdate);

        City cityAfterUpdate = jdbcCityDao.getCityById(id);

        Assert.assertEquals(cityBeforeUpdate, cityAfterUpdate);

    }
}