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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCountryDao implements CountryDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCountryDao.class);
    private static final CountryMapper COUNTRY_MAPPER = new CountryMapper();
    private static final LanguageMapper COUNTRY_LANGUAGE_MAPPER = new LanguageMapper();
    public static final String GET_CAPITAL_ID = "select c.id from city c where c.name = ?";
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

    private static final String ADD_COUNTRY = "INSERT INTO country (code," +
            "name," +
            "continent," +
            "region," +
            "surfacearea," +
            "indepyear," +
            "population," +
            "lifeexpectancy," +
            "governmentform," +
            "headofstate, " +
            "capital ) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE_COUNTRY = "UPDATE country as c set c.name=?, c.continent = ?," +
            "c.region = ?,c.surfacearea = ?, c.indepyear =?, c.population=?," +
            " c.lifeexpectancy=?, c.governmentform=?,c.headofstate = ?,c.capital =?";

    private static final String DELETE_COUNTRY = "delete from country where name =?";

    private DataSource dataSource;

    public JdbcCountryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Country getCountry(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LANGUAGE_STATISTICS)) {
            logger.info("Getting data from SQL query: {} ", GET_LANGUAGE_STATISTICS);

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

            logger.info("Getting data from SQL query: {}", GET_COUNTRIES_BY_LANGUAGE);
            preparedStatement.setString(1, language);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Country> countries = new ArrayList<>();
                while (resultSet.next()) {
                    countries.add(COUNTRY_MAPPER.mapRow(resultSet));
                }
                return countries;
            }
        } catch (SQLException e) {
            logger.error("Unable to execute sql query: {}", GET_COUNTRIES_BY_LANGUAGE, e);
            throw new RuntimeException("Unable to execute sql query: " + GET_COUNTRIES_BY_LANGUAGE, e);
        }
    }

    @Override
    public void add(Country country) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement capitalStatement = connection.prepareStatement(GET_CAPITAL_ID);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_COUNTRY)) {

            countryProcessing(country, capitalStatement, preparedStatement);
            logger.info("Country is successfully inserted {}", country);

        } catch (SQLException e) {
            logger.error("Unable to insert data in sql table {}", country);
            throw new RuntimeException("Unable to save country: " + country, e);

        }
    }

    @Override
    public void delete(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COUNTRY)) {

            preparedStatement.setString(1, name);

            logger.info("Deleting country with name {}", name);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Country country) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement capitalStatement = connection.prepareStatement(GET_CAPITAL_ID);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COUNTRY)) {

            countryProcessing(country, capitalStatement, preparedStatement);
            logger.info("Country is successfully updated {}", country);

        } catch (SQLException e) {
            throw new RuntimeException("Unable to update country", e);
        }
    }

    private void countryProcessing(Country country, PreparedStatement capitalStatement, PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet capitalResultSet = capitalStatement.executeQuery()) {
            int capitalId = capitalResultSet.getInt("id");

            preparedStatement.setString(1, country.getCode());
            preparedStatement.setString(2, country.getName());
            preparedStatement.setString(3, country.getContinent());
            preparedStatement.setString(4, country.getRegion());
            preparedStatement.setDouble(5, country.getSurfaceArea());
            preparedStatement.setInt(6, country.getIndepYear());
            preparedStatement.setLong(7, country.getPopulation());
            preparedStatement.setDouble(8, country.getLifeExpectancy());
            preparedStatement.setString(9, country.getGovernmentForm());
            preparedStatement.setInt(10, capitalId);
            preparedStatement.execute();
        }
    }
}


