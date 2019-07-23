package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.CitySearchCriteria;
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

        CitySearchCriteria citySearchCriteria = new CitySearchCriteria();

        citySearchCriteria.setCountry( "al");
        citySearchCriteria.setName("iv");
        citySearchCriteria.setContinent("Europe");

       String cityByCriteriaQuery = jdbcCityDao.getCityByCriteriaQuery(citySearchCriteria);
        Assert.assertNotNull(cityByCriteriaQuery);
    }
}