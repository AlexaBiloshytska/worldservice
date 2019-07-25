package com.alexa.worldservice.dao.jdbc;

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
    }
}