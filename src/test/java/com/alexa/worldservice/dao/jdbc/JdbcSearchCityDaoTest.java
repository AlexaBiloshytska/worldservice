package com.alexa.worldservice.dao.jdbc;

import org.junit.Test;
import org.mockito.Mock;

import javax.sql.DataSource;

public class JdbcSearchCityDaoTest {
    @Mock
    private DataSource dataSource;

    @Test
    public void getGetCityByCriteriaQuery() {
        JdbcCityDao jdbcCityDao = new JdbcCityDao(dataSource);

        String cityByCriteriaQuery = jdbcCityDao.getCityByCriteriaQuery(Boolean.parseBoolean("al"),Boolean.parseBoolean("iv"),Boolean.parseBoolean("Europe"));
        System.out.println(cityByCriteriaQuery);

    }
}