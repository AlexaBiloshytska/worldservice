package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.dao.CountryDao;
import com.alexa.worldservice.mapper.CountryMapper;
import com.alexa.worldservice.mapper.LanguageMapper;
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
    private final Logger logger = LoggerFactory.getLogger(getClass());
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
            "l.percentage " +
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

    private static final String GET_COUNTRY_BY_NAME = "select c.code,\n" +
            "c.name," +
            "c.continent, " +
            "c.region, " +
            "c.surfacearea, " +
            "c.indepyear, " +
            "c.headofstate " +
            "from country as c --left join city as t on (t.id = c.capital) " +
            "where lower(c.name) like %?% ||lower(%?%)||%? " +
            "AND c.continent = ? " +
            "AND c.population >= 1000;";

    private DataSource dataSource;

    public JdbcCountryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Country getCountry(String name) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LANGUAGE_STATISTICS)) {

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Country country = COUNTRY_MAPPER.mapRow(resultSet);

            List<Language> languages = new ArrayList<>();
            languages.add(COUNTRY_LANGUAGE_MAPPER.mapRow(resultSet));
            while (resultSet.next()) {
                languages.add(COUNTRY_LANGUAGE_MAPPER.mapRow(resultSet));
            }

            country.setLanguageList(languages);
            return country;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query: " + GET_LANGUAGE_STATISTICS, e);
        }
    }

    @Override
    public List<Country> getCountriesByLanguage(String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_COUNTRIES_BY_LANGUAGE);) {

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
    public List<Country> searchByName(String name, String continent, Integer population) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_COUNTRY_BY_NAME)) {
            if (name != null && !name.isEmpty()) {
                String queryName = GET_COUNTRY_BY_NAME + "where lower(c.name) like? OR lower(?)OR?";
            }
            if (continent != null && !continent.isEmpty()) {
                String queryContinent = GET_COUNTRY_BY_NAME + "AND c.continent =?";
            }
            if (population != null) {
                String queryPopulation = GET_COUNTRY_BY_NAME + "AND c.population >= 1000";
            }

            preparedStatement.setString(1, "name");
            preparedStatement.setString(2, "continent");
            preparedStatement.setInt(3, population);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
           List <Country >country = COUNTRY_MAPPER.mapRow(resultSet);

            return country;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query: " + GET_COUNTRY_BY_NAME, e);
        }
    }


}


