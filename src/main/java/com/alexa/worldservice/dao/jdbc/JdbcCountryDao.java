package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.dao.CountryDao;
import com.alexa.worldservice.exception.NoDataFoundException;
import com.alexa.worldservice.mapper.CountryMapper;
import com.alexa.worldservice.mapper.LanguageMapper;
import com.shelberg.entity.Country;
import com.shelberg.entity.Language;
import com.shelberg.search.CountrySearchQuery;
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
    private static final String GET_CAPITAL_NAME = "select c.id from city c where c.name = ?";
    private static final String GET_LANGUAGE_STATISTICS = "select c.*, " +
            "l.language, " +
            "l.isOfficial, " +
            "l.percentage " +
            "from country as c " +
            "inner join country_language as l ON c.code = l.countrycode " +
            "where c.name = ?";

    private static final String GET_COUNTRIES_BY_LANGUAGE = "select c.code,c.name, " +
            "c.continent,c.region,c.surfacearea," +
            "c.indepyear,c.population,c.lifeexpectancy,c.governmentform," +
            "c.headofstate,t.name as capital, c.code2  from country as c " +
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

    private static final String ADD_COUNTRY =
            "INSERT INTO country (name," +
                    "continent," +
                    "region," +
                    "surfacearea," +
                    "indepyear," +
                    "population," +
                    "lifeexpectancy," +
                    "governmentform," +
                    "headofstate, " +
                    "capital, " +
                    "code2," +
                    "code) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE_COUNTRY = "UPDATE country set " +
            "name=?, " +
            "continent = ?," +
            "region = ?," +
            "surfacearea = ?," +
            "indepyear =?, " +
            "population=?," +
            "lifeexpectancy=?, " +
            "governmentform=?,headofstate = ?,capital =?, code2 =? where code =?";

    private static final String DELETE_COUNTRY_BY_CODE = "delete from country where code =?";

    private static final String GET_COUNTRY_BY_CODE = "select cy.name as capital, c.*  from country c " +
            "left join city cy on (c.capital = cy.id) where code = ?";

    private DataSource dataSource;

    public JdbcCountryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Country getCountryStatistics(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LANGUAGE_STATISTICS)) {
            logger.info("Executing SQL query: {}", GET_LANGUAGE_STATISTICS);

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    logger.warn("resultSet is empty");
                    throw new NoDataFoundException("Non-empty resultSet expected");
                }
                Country country = COUNTRY_MAPPER.mapRow(resultSet);

                List<Language> languages = new ArrayList<>();
                languages.add(COUNTRY_LANGUAGE_MAPPER.mapRow(resultSet));
                while (resultSet.next()) {
                    languages.add(COUNTRY_LANGUAGE_MAPPER.mapRow(resultSet));
                }

                country.setLanguageList(languages);
                logger.info("Getting the country with name: {}", country);
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

            logger.info("Executing SQL query {}: ", GET_COUNTRIES_BY_LANGUAGE);
            preparedStatement.setString(1, language);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Country> countries = new ArrayList<>();
                while (resultSet.next()) {
                    countries.add(COUNTRY_MAPPER.mapRow(resultSet));
                }
                logger.info("Getting countries by language: {}",countries);
                return countries;
            }
        } catch (SQLException e) {
            logger.error("Unable to execute sql query: {}", GET_COUNTRIES_BY_LANGUAGE, e);
            throw new RuntimeException("Unable to execute sql query: " + GET_COUNTRIES_BY_LANGUAGE, e);
        }
    }

    @Override
    public List<Country> searchByCriteria(CountrySearchQuery countrySearchQuery) {
        String criteriaQuery = getCountryCriteriaQuery(countrySearchQuery);
        logger.info("Executing SQL query {}: ", countrySearchQuery);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(criteriaQuery)) {
                List<Country> countries = new ArrayList<>();
                while (resultSet.next()) {
                    countries.add(COUNTRY_MAPPER.mapRow(resultSet));
                }
                logger.info("Getting countries with criteria {}",countries);
                return countries;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query: " + GET_COUNTRY_BY_CRITERIA, e);
        }
    }

    String getCountryCriteriaQuery(CountrySearchQuery countrySearchQuery) {
        StringBuilder stringBuilder = new StringBuilder(GET_COUNTRY_BY_CRITERIA);
        logger.info("Starting getting country with parameters {}", stringBuilder);
        if (countrySearchQuery.getName() != null && !countrySearchQuery.getName().isEmpty()) {
            stringBuilder
                    .append(" AND lower(c.name) like '%")
                    .append(countrySearchQuery.getName().toLowerCase())
                    .append("%'");
        }
        if (countrySearchQuery.getContinent() != null && !countrySearchQuery.getContinent().isEmpty()) {
            stringBuilder
                    .append(" AND lower(c.continent) = '")
                    .append(countrySearchQuery.getContinent().toLowerCase())
                    .append("'");
        }
        if (countrySearchQuery.getPopulation() != null && countrySearchQuery.getPopulation() > 0) {
            stringBuilder
                    .append(" AND c.population >= ")
                    .append(countrySearchQuery.getPopulation());
        }
        if (countrySearchQuery.getPage() != null && countrySearchQuery.getPage() > 0) {
            int offset = (countrySearchQuery.getPage() - 1) * countrySearchQuery.getLimit();
            stringBuilder
                    .append(" LIMIT ")
                    .append(countrySearchQuery.getLimit())
                    .append(" OFFSET ")
                    .append(offset);
        }
        logger.info("Finishing getting country with parameters{}", stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    public Country add(Country country) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement capitalStatement = connection.prepareStatement(GET_CAPITAL_NAME);
             PreparedStatement addCountry = connection.prepareStatement(ADD_COUNTRY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            logger.info("Executing SQL query {}: ",ADD_COUNTRY);
            capitalStatement.setString(1, country.getCapital());
            countryProcessing(country, capitalStatement, addCountry);

            try(ResultSet generatedKeys = addCountry.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return COUNTRY_MAPPER.mapRow(generatedKeys);
                }
            }
            logger.info("Country is successfully inserted {}", country);
            throw new RuntimeException("Unable to add country:" + country);

        } catch (SQLException e) {
            logger.error("Unable to insert data in sql table {}", country);
            throw new RuntimeException("Unable to save country: " + country, e);
        }
    }

    @Override
    public int delete(String code) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteCountry = connection.prepareStatement(DELETE_COUNTRY_BY_CODE)) {
            logger.info("Executing SQL query {}", DELETE_COUNTRY_BY_CODE);

            deleteCountry.setString(1, code);
            int affectedRows = deleteCountry.executeUpdate();

            logger.info("Deleting country with name {}", code);
            return affectedRows;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete country", e);
        }
    }

    @Override
    public Country update(Country country) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement capitalStatement = connection.prepareStatement(GET_CAPITAL_NAME);
             PreparedStatement updateCountry = connection.prepareStatement(UPDATE_COUNTRY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            logger.info("Executing SQL query {}", UPDATE_COUNTRY);

            capitalStatement.setString(1, country.getCapital());

            countryProcessing(country, capitalStatement, updateCountry);
            logger.info("Country is successfully updated {}", country);

            try(ResultSet generatedKeys = updateCountry.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return COUNTRY_MAPPER.mapRow(generatedKeys);
                }
            }
            throw new RuntimeException("Unable to get generated keys after country update: " + country);

        } catch (SQLException e) {
            throw new RuntimeException("Unable to update country", e);
        }
    }

    @Override
    public Country getCountryByCode(String code) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_COUNTRY_BY_CODE)) {

            logger.info("Executing SQL query {}", GET_COUNTRY_BY_CODE);

            preparedStatement.setString(1, code);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    logger.error("resultSet is empty");
                    throw new NoDataFoundException("Non-empty resultSet expected");
                }
                return COUNTRY_MAPPER.mapRow(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to get country with code ", e);
        }
    }


    private int countryProcessing(Country country, PreparedStatement capitalStatement, PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet capitalResultSet = capitalStatement.executeQuery()) {
            capitalResultSet.next();
            int capitalId = capitalResultSet.getInt("id");

            preparedStatement.setString(1, country.getName());
            preparedStatement.setString(2, country.getContinent());
            preparedStatement.setString(3, country.getRegion());
            preparedStatement.setDouble(4, country.getSurfaceArea());
            preparedStatement.setInt(5, country.getIndepYear());
            preparedStatement.setLong(6, country.getPopulation());
            preparedStatement.setDouble(7, country.getLifeExpectancy());
            preparedStatement.setString(8, country.getGovernmentForm());
            preparedStatement.setString(9, country.getHeadOfState());
            preparedStatement.setInt(10, capitalId);
            preparedStatement.setString(11, country.getCode2());
            preparedStatement.setString(12, country.getCode());
            return preparedStatement.executeUpdate();
        }
    }
}