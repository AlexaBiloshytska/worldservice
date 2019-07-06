package alexa.com.worldservice.dao.jdbc;

import alexa.com.worldservice.dao.CountryDao;
import alexa.com.worldservice.entity.Country;
import alexa.com.worldservice.entity.CountryLanguageStatistics;
import alexa.com.worldservice.mapper.CountryLanguageMapper;
import alexa.com.worldservice.mapper.CountryMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcCountryDao implements CountryDao {
    private DataSource dataSource;
    private static final CountryMapper COUNTRY_MAPPER = new CountryMapper();
    private static final CountryLanguageMapper COUNTRY_LANGUAGE_STATISTICS_MAPPER = new CountryLanguageMapper();
    private static final String GET_ALL_SQL = "select * from country";
    private static final String GET_LANGUAGE_STATISTICS =
            " select c.Code,c.Name,c.continent,c.region,c.surfacearea,c.indepyear,c.population,l.Language,l.IsOfficial,l.Percentage from country as c Inner join country_language as l ON c.code = l.countrycode";

    public JdbcCountryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Country> getAll() {
        List<Country> countries = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_LANGUAGE_STATISTICS);) {
            while (resultSet.next()) {
                Country category = COUNTRY_MAPPER.mapRow(resultSet);
                countries.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }


    public List<CountryLanguageStatistics> getStatistics(){
        List<CountryLanguageStatistics> countryLanguageStatistics = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_LANGUAGE_STATISTICS);) {
            while (resultSet.next()) {
                CountryLanguageStatistics category = COUNTRY_LANGUAGE_STATISTICS_MAPPER.mapRow(resultSet);
                countryLanguageStatistics.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryLanguageStatistics;
    }

}

