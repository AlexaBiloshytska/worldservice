package com.alexa.worldservice.dao.jdbc;

import org.junit.Test;
import org.mockito.Mock;

import javax.sql.DataSource;

import static org.junit.Assert.*;

public class JdbcCityDaoTest {
    @Mock
    private DataSource dataSource;

    @Test
    public void getGetCityByCriteriaQuery() {
        JdbcCityDao jdbcCityDao = new JdbcCityDao(dataSource);

        String cityByCriteriaQuery = jdbcCityDao.getGetCityByCriteriaQuery("al","iv","Europe");
        System.out.println(cityByCriteriaQuery);

    }
}