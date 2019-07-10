package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.dao.CountryDao;
import com.alexa.worldservice.entity.Country;
import com.alexa.worldservice.entity.CountryLanguage;
import com.alexa.worldservice.entity.CountryLanguageStatistics;
import com.alexa.worldservice.mapper.CountryLanguageMapper;
import com.alexa.worldservice.mapper.CountryMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCountryDao implements CountryDao {
    private static final CountryMapper COUNTRY_MAPPER = new CountryMapper();
    private static final CountryLanguageMapper COUNTRY_LANGUAGE_MAPPER = new CountryLanguageMapper();
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

    private DataSource dataSource;

    public JdbcCountryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Country getCountry(String name) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LANGUAGE_STATISTICS);) {

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Country country = COUNTRY_MAPPER.mapRow(resultSet);

            List<CountryLanguage> countryLanguages = new ArrayList<>();
            countryLanguages.add(COUNTRY_LANGUAGE_MAPPER.mapRow(resultSet));
            while (resultSet.next()){
                countryLanguages.add(COUNTRY_LANGUAGE_MAPPER.mapRow(resultSet));
            }

            country.setCountryLanguages(countryLanguages);
            return country;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query: " + GET_LANGUAGE_STATISTICS, e);
        }
    }

}

