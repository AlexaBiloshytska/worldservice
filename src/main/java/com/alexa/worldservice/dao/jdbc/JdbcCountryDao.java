package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.dao.CountryDao;
import com.alexa.worldservice.exception.NoDataFoundException;
import com.alexa.worldservice.mapper.LanguageMapper;
import com.alexa.worldservice.mapper.CountryMapper;
import com.shelberg.entity.Country;
import com.shelberg.entity.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCountryDao implements CountryDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCountryDao.class);
    private static final CountryMapper COUNTRY_MAPPER = new CountryMapper();
    private static final LanguageMapper COUNTRY_LANGUAGE_MAPPER = new LanguageMapper();
    private static final String GET_LANGUAGE_STATISTICS = "select c.code," +
            "c.name, " +
            "c.continent, " +
            "c.region, " +
            "c.surfacearea, " +
            "c.indepyear, " +
            "c.population, " +
            "l.language, " +
            "l.isOfficial, " +
            "l.percentage, " +
            "c.lifeexpectancy, " +
            "c.governmentform, " +
            "c.headofstate, " +
            "c.capital " +
            "from country as c " +
            "inner join country_language as l ON c.code = l.countrycode " +
            "where c.name = ?";

    private static final String GET_COUNTRIES_BY_LANGUAGE = "select c.code,c.name, " +
            "c.continent,c.region,c.surfacearea," +
            "c.indepyear,c.population,c.lifeexpectancy,c.governmentform," +
            "c.headofstate,t.name as capital  from country as c " +
            "join country_language as lang on lang.countrycode = c.code " +
            "left join city as t on t.id = c.capital " +
            "where lang.language = ?";

    private static final String GET_COUNTRY_BY_CRITERIA = "select c.code," +
            "c.name, " +
            "c.continent, " +
            "c.region, " +
            "c.surfacearea, " +
            "c.indepyear, " +
            "c.headofstate, " +
            "c.population, " +
            "c.lifeexpectancy, " +
            "c.governmentform," +
            "t.name as capital, " +
            "c.code2 " +
            "from country as c " +
            "left join city as t on t.id = c.capital " +
            "where 1=1";

    private DataSource dataSource;

    public JdbcCountryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Country getCountry(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LANGUAGE_STATISTICS)) {
            logger.info("Getting data from SQL query: ", GET_LANGUAGE_STATISTICS);

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    logger.error("resultSet is empty");
                    throw new NoDataFoundException("Non-empty resultSet expected");
                }

                Country country = COUNTRY_MAPPER.mapRow(resultSet);

                List<Language> languages = new ArrayList<>();
                languages.add(COUNTRY_LANGUAGE_MAPPER.mapRow(resultSet));
                while (resultSet.next()) {
                    languages.add(COUNTRY_LANGUAGE_MAPPER.mapRow(resultSet));
                }

                country.setLanguageList(languages);
                return country;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query: " + GET_LANGUAGE_STATISTICS, e);
        }
    }

    @Override
    public List<Country> getCountriesByLanguage(String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_COUNTRIES_BY_LANGUAGE)) {

            logger.info("Getting data from SQL query {}: ", GET_COUNTRIES_BY_LANGUAGE);
            preparedStatement.setString(1, language);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Country> countries = new ArrayList<>();
                while (resultSet.next()) {
                    countries.add(COUNTRY_MAPPER.mapRow(resultSet));
                }
                return countries;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query: " + GET_COUNTRIES_BY_LANGUAGE, e);
        }
    }

    @Override
    public List<Country> searchByCriteria(String name, String continent, Integer population, Integer page, Integer limit) {
        String criteriaQuery = getCountryCriteriaQuery(name, continent, population, page, limit);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(criteriaQuery);
            List<Country> countries = new ArrayList<>();
            while (resultSet.next()) {
                countries.add(COUNTRY_MAPPER.mapRow(resultSet));
            }
            return countries;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query: " + GET_COUNTRY_BY_CRITERIA, e);
        }
    }

    String getCountryCriteriaQuery(String name, String continent, Integer population, Integer page, Integer limit) {
        String defaultQuery = GET_COUNTRY_BY_CRITERIA;
        if (name != null && !name.isEmpty()) {
            defaultQuery = defaultQuery + " AND lower(c.name) like '%" + name.toLowerCase() + "%'";
        }
        if (continent != null && !continent.isEmpty()) {
            defaultQuery = defaultQuery + " AND lower(c.continent) = '" + continent.toLowerCase() + "'";
        }
        if (population != null && population > 0) {
            defaultQuery = defaultQuery + " AND c.population >= " + population + "";
        }
        if (page != null && page > 0) {
            int offset = (page - 1) * limit;
            defaultQuery = defaultQuery + " LIMIT " + limit + " OFFSET " + offset;
        }
        return defaultQuery;
    }
}


