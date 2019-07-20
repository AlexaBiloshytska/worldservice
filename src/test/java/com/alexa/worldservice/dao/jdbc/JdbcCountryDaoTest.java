package com.alexa.worldservice.dao.jdbc;

import com.shelberg.search.CountrySearchQuery;
import org.junit.Test;
import org.mockito.Mock;

import javax.sql.DataSource;

public class JdbcCountryDaoTest {

    @Mock
    private DataSource dataSource;

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

        String countryCriteriaQuery = jdbcCountryDao.getCountryCriteriaQuery(countrySearchQuery);
        System.out.println(countryCriteriaQuery);
    }
}
