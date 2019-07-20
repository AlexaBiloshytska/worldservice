package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.CitySearchCriteria;
import com.shelberg.search.CitySearchQuery;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import javax.sql.DataSource;

public class JdbcSearchCityDaoTest {
    @Mock
    private DataSource dataSource;

    @Test
    public void getGetCityByCriteriaQuery() {
        JdbcCityDao jdbcCityDao = new JdbcCityDao(dataSource);

        String country = "al";
        String name = "iv";
        String continent = "Europe";
        CitySearchQuery citySearchQuery = new CitySearchQuery.Builder(country, name, continent).build();

        String cityByCriteriaQuery = jdbcCityDao.getCityByCriteriaQuery(citySearchQuery);
        Assert.assertNotNull(cityByCriteriaQuery);
    }
}