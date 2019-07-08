package alexa.com.worldservice.dao.jdbc;

import alexa.com.worldservice.entity.CountryLanguageStatistics;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcCountryDaoTest {
    private BasicDataSource dataSource;

    @Before
    public void setup() {
        dataSource = new BasicDataSource();
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:h2:mem:test;INIT=runscript from 'classpath:init.sql'");
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(10);
        dataSource.setMaxIdle(20);
        dataSource.setMaxOpenPreparedStatements(180);
    }

    @Test
    public void getStatistics() {
        // Prepare
        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao(dataSource);
        String countryName = "Aruba";

        // Execute
        CountryLanguageStatistics languageStatistics = jdbcCountryDao.getStatistics(countryName);

        Assert.assertEquals("ABW", languageStatistics.getCode());
        Assert.assertEquals("Aruba", languageStatistics.getName());
        Assert.assertEquals("North America", languageStatistics.getContinent());
        Assert.assertEquals("Caribbean", languageStatistics.getRegion());
        Assert.assertEquals("French", languageStatistics.getLanguage());
        Assert.assertEquals(193, languageStatistics.getSurfaceArea(), 0.01);

    }
}