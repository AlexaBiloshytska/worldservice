package com.alexa.worldservice.dao.jdbc;

import org.junit.Test;
import org.mockito.Mock;

import javax.sql.DataSource;

public class JdbcCountryDaoTest {

    @Mock
    private DataSource dataSource;

    @Test
    public void searchByName() {

        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);
        String countryCriteriaQuery = jdbcCountryDao.getCountryCriteriaQuery("Ukraine", "Europe", 45000, 2);
        System.out.println(countryCriteriaQuery);
    }
}
