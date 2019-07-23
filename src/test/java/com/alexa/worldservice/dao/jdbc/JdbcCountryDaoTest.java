package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.CountrySearchCriteria;
import org.junit.Test;
import org.mockito.Mock;

import javax.sql.DataSource;

public class JdbcCountryDaoTest {

    @Mock
    private DataSource dataSource;

    @Test
    public void searchByName() {

        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);
        CountrySearchCriteria countrySearchCriteria = new CountrySearchCriteria();

        countrySearchCriteria.setName("ang");
        countrySearchCriteria.setContinent("Europe");
        countrySearchCriteria.setPopulation(100);
        countrySearchCriteria.setPage(1);
        countrySearchCriteria.setLimit(5);

        String countryCriteriaQuery = jdbcCountryDao.getCountryCriteriaQuery(countrySearchCriteria);
        System.out.println(countryCriteriaQuery);
    }
}
