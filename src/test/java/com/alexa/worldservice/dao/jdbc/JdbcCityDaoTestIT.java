package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.SearchCity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JdbcCityDaoTestIT {
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
    public void searchCityByCriteria() {
        JdbcCityDao jdbcCityDao = new JdbcCityDao(dataSource);

        String country = "al";
        String name = "iv";
        String continent= "Europe";


        List<SearchCity> cities = jdbcCityDao.searchCityByCriteria(true,true, false, country,name,continent);

        Assert.assertNotNull(cities);

    }
}