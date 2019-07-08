package alexa.com.worldservice.dao.jdbc;

import alexa.com.worldservice.dao.CountryDao;
import alexa.com.worldservice.entity.CountryLanguageStatistics;
import alexa.com.worldservice.mapper.CountryLanguageMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcCountryDao implements CountryDao {
    private DataSource dataSource;
    private static final CountryLanguageMapper COUNTRY_LANGUAGE_STATISTICS_MAPPER = new CountryLanguageMapper();
    private static final String GET_LANGUAGE_STATISTICS = "select c.code," +
            "c.name, " +
            "c.continent, " +
            "c.region, " +
            "c.surfacearea, " +
            "c.indepyear, " +
            "c.population, " +
            "l.Language, " +
            "l.IsOfficial, " +
            "l.Percentage " +
            "from country as c " +
            "inner join country_language as l ON c.code = l.countrycode " +
            "where c.name = ?";

    public JdbcCountryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CountryLanguageStatistics getStatistics(String name) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LANGUAGE_STATISTICS)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return COUNTRY_LANGUAGE_STATISTICS_MAPPER.mapRow(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query: " + GET_LANGUAGE_STATISTICS, e);
        }
    }

}

